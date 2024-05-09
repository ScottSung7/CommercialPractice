package com.example.accountapi.application.applications.signUp;

import com.example.accountapi.TestConfiguration_Service;
import com.example.accountapi.application.tools.provider.emailVerification.EmailVerificationProvider;
import com.example.accountapi.domain.model.Customer;
import com.example.accountapi.domain.model.Seller;
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
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.ArgumentMatchers.any;


@SpringBootTest(classes = {TestConfiguration_Service.class})
@ExtendWith(MockitoExtension.class)
class SignUpApplicationTest {

    @Autowired
    private SignUpApplication signUpApplication;
    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private EmailVerificationProvider emailVerificationProvider;
    @Mock
    CustomerSignUpForm customerSignUpForm;

    String signUpEmail = "tester@test.com";  String signUpName = "tester";
    String signUpPassword = "1234";             String signUpPhone = "01000000000";
    LocalDate signUpBirth = LocalDate.of(1990, 01, 01);
    @Test
    void customerSignUp() {
        //given
        given(customerSignUpForm.getEmail()).willReturn(signUpEmail);
        given(customerSignUpForm.getName()).willReturn(signUpName);
        given(customerSignUpForm.getPassword()).willReturn(signUpPassword);
        given(customerSignUpForm.getPhone()).willReturn(signUpPhone);
        given(customerSignUpForm.getBirth()).willReturn(signUpBirth);

        given(emailVerificationProvider.sendVerificationEmail(anyString(), anyString(), anyString())).willReturn(true); //mail은 mocking처리 되어 있음.
        //when
        Customer customer = signUpApplication.customerSignUp(customerSignUpForm);
        
        //then
        assertTrue(customer.getEmail().equals(signUpEmail));
        assertTrue(customer.getName().equals(signUpName));
        assertTrue(encoder.matches(signUpPassword, customer.getPassword()));
        assertTrue(customer.getPhone().equals(signUpPhone));
        assertTrue(customer.getBirth().equals(signUpBirth));

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
        given(customerUpdateForm.getEmail()).willReturn(updatedEmail);
        given(customerUpdateForm.getName()).willReturn(updatedName);
        given(customerUpdateForm.getPassword()).willReturn(updatedPassword);
        given(customerUpdateForm.getPhone()).willReturn(updatedPhone);
        given(customerUpdateForm.getBirth()).willReturn(updatedBirth);
        given(customerUpdateForm.getMembership()).willReturn(updatedMembership);

        //when
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
        given(sellerSignUpForm.getName()).willReturn(signUpName);
        given(sellerSignUpForm.getPassword()).willReturn(signUpPassword);
        given(sellerSignUpForm.getPhone()).willReturn(signUpPhone);
        given(sellerSignUpForm.getBirth()).willReturn(signUpBirth);

        given(emailVerificationProvider.sendVerificationEmail(anyString(), anyString(), anyString())).willReturn(true); //mail은 mocking처리 되어 있음.
        //when
        Seller seller = signUpApplication.sellerSignUp(sellerSignUpForm);

        //then
        assertTrue(seller.getEmail().equals(signUpEmail));
        assertTrue(seller.getName().equals(signUpName));
        assertTrue(encoder.matches(signUpPassword, seller.getPassword()));
        assertTrue(seller.getPhone().equals(signUpPhone));
        assertTrue(seller.getBirth().equals(signUpBirth));
    }
    @Mock
    private SellerUpdateForm sellerUpdateForm;
    String updatedCompanyRegistrationNumber = "AA211112";
    @Test
    void sellerUpdate() {
        //given
        given(sellerUpdateForm.getEmail()).willReturn(updatedEmail);
        given(sellerUpdateForm.getName()).willReturn(updatedName);
        given(sellerUpdateForm.getPassword()).willReturn(updatedPassword);
        given(sellerUpdateForm.getPhone()).willReturn(updatedPhone);
        given(sellerUpdateForm.getBirth()).willReturn(updatedBirth);
        given(sellerUpdateForm.getCompanyRegistrationNumber()).willReturn(updatedCompanyRegistrationNumber);

        //when
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
        String email = "tester@test.com";
        //when
        boolean check = signUpApplication.customerVerify(email);
        //then
        assertTrue(check);
    }

    @Test
    void sellerVerify() {
        //given
        String email = "tester@test.com";
        //when
        boolean check = signUpApplication.sellerVerify(email);
        //then
        assertTrue(check);
    }
}