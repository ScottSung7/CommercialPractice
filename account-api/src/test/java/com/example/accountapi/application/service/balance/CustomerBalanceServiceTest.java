package com.example.accountapi.application.service.balance;

import com.example.accountapi.TestConfiguration_Service_Mock;
import com.example.accountapi.domain.model.Customer;
import com.example.accountapi.domain.model.CustomerBalanceHistory;
import com.example.accountapi.repository.customer.CustomerBalanceHistoryRepository;
import com.example.accountapi.repository.customer.CustomerRepository;
import com.example.accountapi.web.validation.exception.AccountException;
import com.example.accountapi.web.validation.form.ChangeBalanceForm;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.Optional;

import static com.example.accountapi.web.validation.exception.ErrorCode.NOT_ENOUGH_BALANCE;
import static com.example.accountapi.web.validation.exception.ErrorCode.NOT_FOUND_USER;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {TestConfiguration_Service_Mock.class})
@ExtendWith(MockitoExtension.class)
class CustomerBalanceServiceTest {

    @Autowired
    private CustomerBalanceService customerBalanceService;

    @Autowired
    private CustomerBalanceHistoryRepository customerBalanceHistoryRepository;
    @Autowired
    private CustomerRepository customerRepository;

    @Mock
    private ChangeBalanceForm changeBalanceForm;

    @Test
    @WithMockUser
    void changeBalance() {
        //given
        String email = "tester@test.com";
        when(changeBalanceForm.getFrom()).thenReturn("sender");
        when(changeBalanceForm.getMessage()).thenReturn("입금");
        when(changeBalanceForm.getMoney()).thenReturn(1000);

        Customer customer = Customer.builder().id(1L).email(email).build();
        CustomerBalanceHistory history =
                CustomerBalanceHistory.builder().id(1L).currentMoney(2000).customer(customer).changedMoney(500).description("예전").fromMessage("sender").build();
        //when
        when(customerRepository.findByEmail(email)).thenReturn(Optional.of(customer));
        when(customerBalanceHistoryRepository.findFirstByCustomer_IdOrderByIdDesc(1L)).thenReturn(Optional.of(history));
        when(customerBalanceHistoryRepository.save(any(CustomerBalanceHistory.class))).thenAnswer(invocation -> invocation.getArgument(0));
        CustomerBalanceHistory customerBalanceHistory = customerBalanceService.changeBalance(email, changeBalanceForm);
        //then
        assertEquals(history.getCurrentMoney() + changeBalanceForm.getMoney(), customerBalanceHistory.getCurrentMoney());
        assertEquals(changeBalanceForm.getMoney(), customerBalanceHistory.getChangedMoney());
        assertEquals(changeBalanceForm.getFrom(), customerBalanceHistory.getFromMessage());
        assertEquals(changeBalanceForm.getMessage(), customerBalanceHistory.getDescription());
        assertEquals(customer.getEmail(), customerBalanceHistory.getCustomer().getEmail());
    }
    @Test
    @WithMockUser
    void changeBalance_NOT_FOUND_USER(){
        //given
        String email = "";
        //when
        AccountException accountException = assertThrows(AccountException.class, () ->
                customerBalanceService.changeBalance(email, changeBalanceForm));
        //then
        assertEquals(NOT_FOUND_USER, accountException.getErrorCode());
    }
    @Test
    @WithMockUser
    void changeBalance_NOT_ENOUGH_BALANCE() {
        //given
        String email = "tester@test.com";
        when(changeBalanceForm.getMoney()).thenReturn(-1000);

        Customer customer = Customer.builder().id(1L).email(email).build();
        CustomerBalanceHistory history =
                CustomerBalanceHistory.builder().id(1L).currentMoney(500).customer(customer).changedMoney(500).description("예전").fromMessage("sender").build();
        //when
        when(customerRepository.findByEmail(email)).thenReturn(Optional.of(customer));
        AccountException accountException = assertThrows(AccountException.class, () ->
                customerBalanceService.changeBalance(email, changeBalanceForm));
        //then
        assertEquals(NOT_ENOUGH_BALANCE, accountException.getErrorCode());
    }

    @Test
    @WithMockUser
    void checkBalance() {
        //given
        String email = "tester@test.com";

        Customer customer = Customer.builder().id(1L).email(email).build();
        CustomerBalanceHistory history =
                CustomerBalanceHistory.builder().id(1L).currentMoney(2000).customer(customer).changedMoney(500).description("예전").fromMessage("sender").build();
        //when
        when(customerRepository.findByEmail(email)).thenReturn(Optional.of(customer));
        when(customerBalanceHistoryRepository.findFirstByCustomer_IdOrderByIdDesc(1L)).thenReturn(Optional.of(history));

        CustomerBalanceHistory customerBalanceHistory = customerBalanceService.checkBalance(email);
        //then
        assertEquals(history.getCurrentMoney(), customerBalanceHistory.getCurrentMoney());
        assertEquals(history.getChangedMoney(), customerBalanceHistory.getChangedMoney());
        assertEquals(history.getDescription(), customerBalanceHistory.getDescription());
        assertEquals(history.getFromMessage(), customerBalanceHistory.getFromMessage());
        assertEquals(customer.getEmail(), customerBalanceHistory.getCustomer().getEmail());
    }
    @Test
    @WithMockUser
    void checkBalance_NoHistoryReturnZero(){
        //given
        String email = "tester@test.com";

        Customer customer = Customer.builder().id(1L).email(email).build();
        //when
        when(customerRepository.findByEmail(email)).thenReturn(Optional.of(customer));
        CustomerBalanceHistory customerBalanceHistory = customerBalanceService.checkBalance(email);
        //then
        assertEquals(0, customerBalanceHistory.getCurrentMoney());
        assertEquals(0, customerBalanceHistory.getChangedMoney());
        assertEquals(customer.getEmail(), customerBalanceHistory.getCustomer().getEmail());

    }
    @Test
    @WithMockUser
    void checkBalance_NOT_FOUND_USER() {
        //given
        String email = "";
        //when
        AccountException accountException = assertThrows(AccountException.class, () ->
                customerBalanceService.checkBalance(email));
        //then
        assertEquals(NOT_FOUND_USER, accountException.getErrorCode());
    }
}