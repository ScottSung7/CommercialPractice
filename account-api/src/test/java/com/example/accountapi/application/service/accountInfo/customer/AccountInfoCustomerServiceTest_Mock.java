package com.example.accountapi.application.service.accountInfo.customer;

import com.example.accountapi.TestConfiguration_Service_Mock;
import com.example.accountapi.domain.model.Customer;
import com.example.accountapi.repository.customer.CustomerRepository;
import com.example.accountapi.web.validation.exception.AccountException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Optional;

import static com.example.accountapi.web.validation.exception.ErrorCode.NOT_FOUND_USER;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;


@SpringBootTest(classes = {TestConfiguration_Service_Mock.class})
@ExtendWith(MockitoExtension.class)
class AccountInfoCustomerServiceTest_Mock {



    @Autowired
    private AccountInfoCustomerService accountInfoCustomerService;

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    void findCustomer() {
        //given
        String email = "tester@test.com";
        Customer customer = Customer.builder().
                email(email).
                name("tester").
                phone("01000000000").
                birth(LocalDate.now()).
                build();
        //when
        given(customerRepository.findByEmail(email)).willReturn(Optional.of(customer));
        Customer customerReturned = accountInfoCustomerService.findCustomer(email);

        //then
        assertTrue(customerReturned.getEmail().equals(email));
        assertTrue(customerReturned.getName().equals("tester"));

    }

    @Test
    void findCustomer_Not_FOUND(){
        //given
        String email = "";
        //when
        AccountException exception
                = assertThrows(AccountException.class, () -> accountInfoCustomerService.findCustomer(email));
        //then
        assertEquals(NOT_FOUND_USER, exception.getErrorCode());

    }
}