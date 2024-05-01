package com.example.accountapi.application.applications.signUp;

import com.example.accountapi.TestConfiguration_Application_Mock;
import com.example.accountapi.TestConfiguration_Service_Mock;
import com.example.accountapi.application.service.signUp.customer.SignUpCustomerService;
import com.example.accountapi.application.service.signUp.seller.SignUpSellerService;
import com.example.accountapi.application.tools.provider.emailVerification.EmailVerificationProvider;
import com.example.accountapi.domain.model.Customer;
import com.example.accountapi.domain.model.Seller;
import com.example.accountapi.repository.customer.CustomerRepository;
import com.example.accountapi.repository.seller.SellerRepository;
import com.example.accountapi.web.validation.exception.AccountException;
import com.example.accountapi.web.validation.form.customer.CustomerSignUpForm;
import com.example.accountapi.web.validation.form.customer.CustomerUpdateForm;
import com.example.accountapi.web.validation.form.seller.SellerSignUpForm;
import com.example.accountapi.web.validation.form.seller.SellerUpdateForm;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static com.example.accountapi.web.validation.exception.ErrorCode.ALREADY_REGISTER_USER;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;


@SpringBootTest(classes = {TestConfiguration_Application_Mock.class})
@ExtendWith(MockitoExtension.class)
class SignUpApplicationTest_Mock {

    @Autowired
    private EmailVerificationProvider emailVerificationProvider;
    @Autowired
    private SignUpCustomerService signUpCustomerService;
    @Autowired
    private SignUpSellerService signUpSellerService;
    @Autowired
    private SignUpApplication signUpApplication;
    @Autowired
    private PasswordEncoder encoder;

    @Mock
    private CustomerSignUpForm customerSignUpForm;
    @Mock
    private Customer customerMock;

    String signUpEmail = "tester@test.com";  String signUpName = "tester";
    String signUpPassword = "1234";             String signUpPhone = "01000000000";
    LocalDate signUpBirth = LocalDate.of(1990, 01, 01);
    @Test
    void customerSignUp() {
        //given
        given(customerSignUpForm.getEmail()).willReturn(signUpEmail);
        Customer customer = Customer.builder()
                .email(signUpEmail)
                .name(signUpName)
                .password(encoder.encode(signUpPassword))
                .phone(signUpPhone)
                .birth(signUpBirth).build();
        //when
        given(emailVerificationProvider.sendVerificationEmail(anyString(), anyString(), anyString())).willReturn(true); //mail은 mocking처리 되어 있음.
        when(signUpCustomerService.isEmailExist(signUpEmail)).thenReturn(false);
        when(signUpCustomerService.signUp(customerSignUpForm)).thenReturn(customer);
        Customer customerReturned = signUpApplication.customerSignUp(customerSignUpForm);
        // then
        assertTrue(customerReturned.getEmail().equals(signUpEmail));
        assertTrue(customerReturned.getName().equals(signUpName));
        assertTrue(encoder.matches(signUpPassword, customerReturned.getPassword()));
        assertTrue(customerReturned.getPhone().equals(signUpPhone));
        assertTrue(customerReturned.getBirth().equals(signUpBirth));

    }
    @Test
    void customerSignUp_ALREADY_REGISTER_USER(){
        //given
        given(customerSignUpForm.getEmail()).willReturn(signUpEmail);
        //when
        when(signUpCustomerService.isEmailExist(signUpEmail)).thenReturn(true);
        AccountException exception
                =  assertThrows(AccountException.class, ()->  signUpApplication.customerSignUp(customerSignUpForm));
        //then
        assertEquals(ALREADY_REGISTER_USER, exception.getErrorCode());
    }

    @Mock
    private CustomerUpdateForm customerUpdateForm;
    String updatedEmail = "tester@test.com";  String updatedName = "changedTester";
    String updatedPassword = "1235";            String updatedPhone = "0000000";
    LocalDate updatedBirth = LocalDate.of(1990, 01, 01);
    String updatedMembership = "VIP";

    @Test
    void customerUpdate() {
        //given
        Customer customer = Customer.builder()
                .email(updatedEmail)
                .name(updatedName)
                .password(encoder.encode(updatedPassword))
                .phone(updatedPhone)
                .birth(updatedBirth)
                .membership(updatedMembership)
                .build();
        //when
        when(signUpCustomerService.update(customerUpdateForm)).thenReturn(customer);
        Customer updatedCustomer = signUpApplication.customerUpdate(customerUpdateForm);
        //then
        assertTrue(updatedCustomer.getEmail().equals(updatedEmail));
        assertTrue(updatedCustomer.getName().equals(updatedName));
        assertTrue(encoder.matches(updatedPassword, updatedCustomer.getPassword()));
        assertTrue(updatedCustomer.getPhone().equals(updatedPhone));
        assertTrue(updatedCustomer.getBirth().equals(updatedBirth));
        assertTrue(updatedCustomer.getMembership().equals(updatedMembership));
    }
    @Mock
    private SellerSignUpForm sellerSignUpForm;
    @Test
    void sellerSignUp() {
        //given
        given(sellerSignUpForm.getEmail()).willReturn(signUpEmail);
        Seller seller = Seller.builder()
                .email(signUpEmail)
                .name(signUpName)
                .password(encoder.encode(signUpPassword))
                .phone(signUpPhone)
                .birth(signUpBirth)
                .companyRegistrationNumber(updatedCompanyRegistrationNumber)
                .build();
        //when
        given(emailVerificationProvider.sendVerificationEmail(anyString(), anyString(), anyString())).willReturn(true); //mail은 mocking처리 되어 있음.
        when(signUpSellerService.signUp(sellerSignUpForm)).thenReturn(seller);
        Seller sellerReturned = signUpApplication.sellerSignUp(sellerSignUpForm);
        //then
        assertTrue(sellerReturned.getEmail().equals(signUpEmail));
        assertTrue(sellerReturned.getName().equals(signUpName));
        assertTrue(encoder.matches(signUpPassword, sellerReturned.getPassword()));
        assertTrue(sellerReturned.getPhone().equals(signUpPhone));
        assertTrue(sellerReturned.getBirth().equals(signUpBirth));
    }
    @Test
    void sellerSignUp_ALREADY_REGISTER_USER(){
        //given
        given(sellerSignUpForm.getEmail()).willReturn(signUpEmail);
        //when
        when(signUpSellerService.isEmailExist(signUpEmail)).thenReturn(true);
        AccountException exception
                =  assertThrows(AccountException.class, ()->  signUpApplication.sellerSignUp(sellerSignUpForm));
        //then
        assertEquals(ALREADY_REGISTER_USER, exception.getErrorCode());
    }
    @Mock
    private SellerUpdateForm sellerUpdateForm;
    String updatedCompanyRegistrationNumber = "AA211112";
    @Test
    void sellerUpdate() {
        //given
        Seller seller = Seller.builder()
                .email(updatedEmail)
                .name(updatedName)
                .password(encoder.encode(updatedPassword))
                .phone(updatedPhone)
                .birth(updatedBirth)
                .companyRegistrationNumber(updatedCompanyRegistrationNumber)
                .build();

        //when
        when(signUpSellerService.update(sellerUpdateForm)).thenReturn(seller);
        Seller updatedSeller = signUpApplication.sellerUpdate(sellerUpdateForm);
        //then
        assertTrue(updatedSeller.getEmail().equals(updatedEmail));
        assertTrue(updatedSeller.getName().equals(updatedName));
        assertTrue(encoder.matches(updatedPassword, updatedSeller.getPassword()));
        assertTrue(updatedSeller.getPhone().equals(updatedPhone));
        assertTrue(updatedSeller.getBirth().equals(updatedBirth));
        assertTrue(updatedSeller.getCompanyRegistrationNumber().equals(updatedCompanyRegistrationNumber));
    }

    @Test
    void customerVerify() {
        //given
        String email = signUpEmail;
        //when
        when(signUpCustomerService.customerVerify(email)).thenReturn(true);
        boolean check = signUpApplication.customerVerify(email);
        //then
        assertTrue(check);
    }

    @Test
    void sellerVerify() {
        //given
        String email = signUpEmail;
        //when
        when(signUpSellerService.sellerVerify(email)).thenReturn(true);
        boolean check = signUpApplication.sellerVerify(email);
        //then
        assertTrue(check);
    }
}