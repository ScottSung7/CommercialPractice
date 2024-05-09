package com.example.accountapi.application.service.signUp.customer;

import com.example.accountapi.domain.model.Customer;
import com.example.accountapi.TestConfiguration_Service;
import com.example.accountapi.repository.customer.CustomerRepository;
import com.example.accountapi.web.validation.exception.AccountException;
import com.example.accountapi.web.validation.form.customer.CustomerSignUpForm;

import com.example.accountapi.web.validation.form.customer.CustomerUpdateForm;
import org.junit.jupiter.api.*;
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

@SpringBootTest(classes = {TestConfiguration_Service.class}) //email은 전송은 유닛 테스트에서 하지 않기 위해 적용.
@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Disabled
public class SignUpCustomerServiceTest {

    @Autowired
    private SignUpCustomerService signUpCustomerService;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    CustomerRepository customerRepository;

    @Mock
    private CustomerSignUpForm customerSignUpForm;

    String signUpEmail= "tester@test.com";      String signUpName = "tester";
    String signUpPassword = "1234";             String signUpPhone = "01000000000";
    LocalDate signUpBirth = LocalDate.of(1990, 01, 01);
    @Test
    @Order(1)
    void signUp() {
        //given
        given(customerSignUpForm.getEmail()).willReturn(signUpEmail);
        given(customerSignUpForm.getName()).willReturn(signUpName);
        given(customerSignUpForm.getPassword()).willReturn(signUpPassword);
        given(customerSignUpForm.getPhone()).willReturn(signUpPhone);
        given(customerSignUpForm.getBirth()).willReturn(signUpBirth);

        // when
        Customer customerReturned = signUpCustomerService.signUp(customerSignUpForm);

        // then
        assertTrue(customerReturned.getEmail().equals(signUpEmail));
        assertTrue(customerReturned.getName().equals(signUpName));
        assertTrue(encoder.matches(signUpPassword, customerReturned.getPassword()));
        assertTrue(customerReturned.getPhone().equals(signUpPhone));
        assertTrue(customerReturned.getBirth().equals(signUpBirth));

    }
    @Test
    @Order(2)
    void signUp_AlreadyExist() {
        //given
        given(customerSignUpForm.getEmail()).willReturn(signUpEmail);
        given(customerSignUpForm.getName()).willReturn(signUpName);
        given(customerSignUpForm.getPassword()).willReturn(signUpPassword);
        given(customerSignUpForm.getPhone()).willReturn(signUpPhone);
        given(customerSignUpForm.getBirth()).willReturn(signUpBirth);

        //when
        AccountException exception
                =  assertThrows(AccountException.class, ()->  signUpCustomerService.signUp(customerSignUpForm));

        //then
        assertEquals(ALREADY_REGISTER_USER, exception.getErrorCode());

    }
    @Mock
    private Customer customer;
    Long signUpId = 1L;
    @Test
    @Order(2)
    void addExpirationDate() {
        //NOTE: 하루가 넘어가는 떄이면 에러가 날 수 있다.

        LocalDateTime now = LocalDateTime.now().plusDays(1);
        //given
        given(customer.getId()).willReturn(signUpId);
        //when
        Customer customerReturned = signUpCustomerService.addExpirationDate(customer, 1);
        //then
        assertTrue(customerReturned.getVerificationExpiredAt().getDayOfYear() == now.getDayOfYear());
    }
    @Test
    void addExpirationDate_NOT_FOUND_USER() {
        //given
        given(customer.getId()).willReturn(0L);
        //when
        AccountException exception
                =  assertThrows(AccountException.class, ()->  signUpCustomerService.addExpirationDate(customer, 1));
        //then
        assertEquals(NOT_FOUND_USER, exception.getErrorCode());

    }
    @Test
    @Order(2)
    void customerVerify() {
        //given
        String email = signUpEmail;
        //when
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
    @Order(3)
    void customerVerify_ALREADY_VERIFIED() {
        //given
        String email = signUpEmail;

        //when
        AccountException exception
                =  assertThrows(AccountException.class, ()->  signUpCustomerService.customerVerify(email));

        //then
        assertEquals(ALREADY_VERIFIED, exception.getErrorCode());
    }
    @Mock
    Customer customerExpired;
    @Test //Repository Mocked. //Instance Manually Injected
    void customerVerify_VERIFICATION_EXPIRED() {
        //given
        String email = signUpEmail;
        CustomerRepository customerRepository = mock(CustomerRepository.class);
        SignUpCustomerService signUpCustomerServiceExpired = new SignUpCustomerServiceImpl(customerRepository, encoder);

        given(customerExpired.getVerificationExpiredAt()).willReturn(LocalDateTime.now().minusDays(1));
        given(customerRepository.findByEmail(email)).willReturn(Optional.of(customerExpired));

        //when
        AccountException exception
                =  assertThrows(AccountException.class, ()->  signUpCustomerServiceExpired.customerVerify(email));

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
    @Order(2)
    void update() {

        //given
        given(customerUpdateForm.getEmail()).willReturn(updatedEmail);
        given(customerUpdateForm.getName()).willReturn(updatedName);
        given(customerUpdateForm.getPassword()).willReturn(updatedPassword);
        given(customerUpdateForm.getPhone()).willReturn(updatedPhone);
        given(customerUpdateForm.getBirth()).willReturn(updatedBirth);
        given(customerUpdateForm.getMembership()).willReturn(updatedMembership);

        //when
        Customer customer = signUpCustomerService.update(customerUpdateForm);

        //then
        assertTrue(customer.getEmail().equals(updatedEmail));
        assertTrue(customer.getName().equals(updatedName));
        assertTrue(encoder.matches(updatedPassword, customer.getPassword()));
        assertTrue(customer.getPhone().equals(updatedPhone));
        assertTrue(customer.getBirth().equals(updatedBirth));
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
    @Order(2)
    void isEmailExist() {
        //given
        String email = "tester@test.com";
        //when
        Boolean check = signUpCustomerService.isEmailExist(email);
        //then
        assertTrue(check);
    }

}