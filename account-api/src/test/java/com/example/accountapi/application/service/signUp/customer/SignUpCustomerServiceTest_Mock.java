package com.example.accountapi.application.service.signUp.customer;

import com.example.accountapi.TestConfiguration_Service_Mock;
import com.example.accountapi.domain.model.Customer;
import com.example.accountapi.repository.customer.CustomerRepository;
import com.example.accountapi.web.validation.exception.AccountException;
import com.example.accountapi.web.validation.form.customer.CustomerSignUpForm;
import com.example.accountapi.web.validation.form.customer.CustomerUpdateForm;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static com.example.accountapi.web.validation.exception.ErrorCode.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {TestConfiguration_Service_Mock.class})
@ExtendWith(MockitoExtension.class)
public class SignUpCustomerServiceTest_Mock {

    @Autowired
    private SignUpCustomerService signUpCustomerService;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private CustomerRepository customerRepository;

    @Mock
    private CustomerSignUpForm customerSignUpForm;
    @Mock
    private Customer customerMock;

    String signUpEmail= "tester@test.com";      String signUpName = "tester";
    String signUpPassword = "1234";             String signUpPhone = "01000000000";
    LocalDate signUpBirth = LocalDate.of(1990, 01, 01);
    @Test
    void signUp() { //내부에서 Customer 생성
        //given
        given(customerSignUpForm.getEmail()).willReturn(signUpEmail);
        given(customerSignUpForm.getName()).willReturn(signUpName);
        given(customerSignUpForm.getPassword()).willReturn(signUpPassword);
        given(customerSignUpForm.getPhone()).willReturn(signUpPhone);
        given(customerSignUpForm.getBirth()).willReturn(signUpBirth);

        // when
        when(customerRepository.save(any(Customer.class))).thenAnswer(invocation -> invocation.getArgument(0));
        Customer customerReturned = signUpCustomerService.signUp(customerSignUpForm);

        // then
        assertTrue(customerReturned.getEmail().equals(signUpEmail));
        assertTrue(customerReturned.getName().equals(signUpName));
        assertTrue(encoder.matches(signUpPassword, customerReturned.getPassword()));
        assertTrue(customerReturned.getPhone().equals(signUpPhone));
        assertTrue(customerReturned.getBirth().equals(signUpBirth));

    }
    @Test
    void signUp_AlreadyExist() {//내부에서 Customer 생성
        //given
        given(customerSignUpForm.getEmail()).willReturn(signUpEmail);
        given(customerSignUpForm.getName()).willReturn(signUpName);
        given(customerSignUpForm.getPassword()).willReturn(signUpPassword);
        given(customerSignUpForm.getPhone()).willReturn(signUpPhone);
        given(customerSignUpForm.getBirth()).willReturn(signUpBirth);

        //when
        when(customerRepository.findByEmail(signUpEmail)).thenReturn(Optional.of(customerMock));
        AccountException exception
                =  assertThrows(AccountException.class, ()->  signUpCustomerService.signUp(customerSignUpForm));
        //then
        assertEquals(ALREADY_REGISTER_USER, exception.getErrorCode());
    }

    Long signUpId = 1L;
    @Test
    void addExpirationDate() {
        //NOTE: 하루가 넘어가는 떄이면 에러가 날 수 있다.
        //given
        LocalDateTime now = LocalDateTime.now();
        Customer customer = Customer.builder().id(signUpId).verificationExpiredAt(now).build();
        //when
        when(customerRepository.findById(signUpId)).thenReturn(Optional.of(customer));
        Customer customerReturned = signUpCustomerService.addExpirationDate(customer, 1);
        //then
        assertTrue(customerReturned.getVerificationExpiredAt().getDayOfYear() == now.plusDays(1).getDayOfYear());
    }

    @Test
    void addExpirationDate_NOT_FOUND_USER() {
        //given
        given(customerMock.getId()).willReturn(0L);
        //when
        AccountException exception
                =  assertThrows(AccountException.class, ()->  signUpCustomerService.addExpirationDate(customerMock, 1));
        //then
        assertEquals(NOT_FOUND_USER, exception.getErrorCode());
    }
    @Test
    void customerVerify() {
        //given
        String email = signUpEmail;
        Customer customer = Customer.builder()
                .email(signUpEmail)
                .verificationExpiredAt(LocalDateTime.now().plusDays(1))
                .build();
        //when
        when(customerRepository.findByEmail(email)).thenReturn(Optional.of(customer));
        boolean check = signUpCustomerService.customerVerify(email);
        //then
        assertTrue(check);
    }
    @Test
    void customerVerify_NOT_FOUND() {
        //given
        String email = "";
        //when
        AccountException exception
                =  assertThrows(AccountException.class, ()->  signUpCustomerService.customerVerify(email));
        //then
        assertEquals(NOT_FOUND_USER, exception.getErrorCode());
    }
    @Test
    void customerVerify_ALREADY_VERIFIED() {
        //given
        String email = signUpEmail;
        //when
        when(customerMock.isVerified()).thenReturn(true);
        when(customerRepository.findByEmail(email)).thenReturn(Optional.of(customerMock));
        AccountException exception
                =  assertThrows(AccountException.class, ()->  signUpCustomerService.customerVerify(email));
        //then
        assertEquals(ALREADY_VERIFIED, exception.getErrorCode());
    }
    @Test
    void customerVerify_VERIFICATION_EXPIRED() {
        //given
        String email = signUpEmail;
        //when
        when(customerMock.getVerificationExpiredAt()).thenReturn(LocalDateTime.now().minusDays(1));
        when(customerRepository.findByEmail(email)).thenReturn(Optional.of(customerMock));
        AccountException exception
                =  assertThrows(AccountException.class, ()->  signUpCustomerService.customerVerify(email));
        //then
        assertEquals(VERIFICATION_EXPIRED, exception.getErrorCode());
    }
    @Mock
    private CustomerUpdateForm customerUpdateForm;
    String updatedEmail = "tester@test.com";    String updatedName = "changedTester";
    String updatedPassword = "1235";            String updatedPhone = "0000000";
    LocalDate updatedBirth = LocalDate.of(1990, 01, 01);
    String updatedMembership = "VIP";
    @Test
    void update() {
        //given
        given(customerUpdateForm.getEmail()).willReturn(updatedEmail);
        given(customerUpdateForm.getName()).willReturn(updatedName);
        given(customerUpdateForm.getPassword()).willReturn(updatedPassword);
        given(customerUpdateForm.getPhone()).willReturn(updatedPhone);
        given(customerUpdateForm.getBirth()).willReturn(updatedBirth);
        given(customerUpdateForm.getMembership()).willReturn(updatedMembership);

        Customer customer = Customer.builder().email(signUpEmail).build();
        Customer customerUpdate = Customer.updateFrom(customerUpdateForm, customer);
        //when
        when(customerRepository.findByEmail(updatedEmail)).thenReturn(Optional.of(customer));
        Customer customerReturned = signUpCustomerService.update(customerUpdateForm);
        //then
        assertTrue(customerReturned.getEmail().equals(customerUpdate.getEmail()));
        assertTrue(customerReturned.getName().equals(customerUpdate.getName()));
        assertTrue(encoder.matches(updatedPassword, customerReturned.getPassword()));
        assertTrue(customerReturned.getPhone().equals(customerUpdate.getPhone()));
        assertTrue(customerReturned.getBirth().equals(customerUpdate.getBirth()));
    }
    @Test
    void update_NOT_FOUND_USER() {
        //given
        given(customerUpdateForm.getEmail()).willReturn("");
        //when
        AccountException exception
                =  assertThrows(AccountException.class, ()->  signUpCustomerService.update(customerUpdateForm));
        //then
        assertEquals(NOT_FOUND_USER, exception.getErrorCode());
    }
    @Test
    void isEmailExist() {
        //given
        String email = "tester@test.com";
        //when
        when(customerRepository.findByEmail(email)).thenReturn(Optional.of(customerMock));
        Boolean check = signUpCustomerService.isEmailExist(email);
        //then
        assertTrue(check);
    }

}