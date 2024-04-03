package com.example.accountapi.application;


import com.example.accountapi.application.applications.signUp.SignUpApplication;
import com.example.accountapi.application.applications.signUp.SignUpApplicationImpl;
import com.example.accountapi.application.provider.emailVerification.EmailVerificationProvider;
import com.example.accountapi.application.service.signUp.customer.SignUpCustomerService;
import com.example.accountapi.application.service.signUp.seller.SignUpSellerService;
import com.example.accountapi.domain.model.Customer;
import com.example.accountapi.domain.model.Seller;
import com.example.accountapi.web.Tester;

import com.example.accountapi.web.validation.exception.AccountException;
import com.example.accountapi.web.validation.form.customer.CustomerSignUpForm;
import com.example.accountapi.web.validation.form.seller.SellerSignUpForm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import static com.example.accountapi.web.validation.exception.ErrorCode.ALREADY_REGISTER_USER;
import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

//@SpringBootTest
//@Import({TestConfiguration.class})
@ExtendWith(MockitoExtension.class)
class SignUpApplicationTest_Mock {

    @Mock
    private SignUpCustomerService signUpCustomerService;
    @Mock
    private SignUpSellerService signUpSellerService;
    @Mock
    private EmailVerificationProvider emailVerificationProvider;

    private SignUpApplication signUpApplication;

    @BeforeEach
    public void beforeEach(){
        signUpApplication = new SignUpApplicationImpl(signUpCustomerService, signUpSellerService, emailVerificationProvider);
    }

    @Test
    @DisplayName("SignUpApplication Mock: signUp_Customer")
    void signUp_Customer() {
        CustomerSignUpForm customerSignUpForm = Tester.customerSignUpForm;
        Customer customer = Customer.from(customerSignUpForm);

        when(signUpCustomerService.isEmailExist(customerSignUpForm.getEmail())).thenReturn(false);
        when(signUpCustomerService.signUp(customerSignUpForm)).thenReturn(customer);

        //when(emailVerificationProvider.sendVerificationEmail(customer)).thenReturn("Code");
        //when(signUpCustomerService.changeCustomerValidateEmail(customer, "Code")).thenReturn(customer);

        System.out.println(signUpApplication.customerSignUp(customerSignUpForm));
    }

    @Test
    @DisplayName("SignUpApplication Mock: signUp_Seller")
    void signUp_Seller() {
        SellerSignUpForm sellerSignUpForm = Tester.sellerSignUpForm;
        Seller seller = Seller.from(sellerSignUpForm);

        when(signUpSellerService.isEmailExist(sellerSignUpForm.getEmail())).thenReturn(false);
        when(signUpSellerService.signUp(sellerSignUpForm)).thenReturn(seller);

        //when(emailVerificationProvider.sendVerificationEmail(seller)).thenReturn("Code");
        //when(signUpSellerService.changeSellerValidateEmail(seller, "Code")).thenReturn(seller);

        System.out.println(signUpApplication.sellerSignUp(sellerSignUpForm));
    }


    @Test
    @DisplayName("signUpCustomerService ALREADY_REGISTER_EXCEPTION.")
    void signUpApplication_alreadyRegisterException() {
        CustomerSignUpForm customerSignUpForm = Tester.customerSignUpForm;
        Customer customer = Customer.from(customerSignUpForm);

        when(signUpCustomerService.isEmailExist(customerSignUpForm.getEmail())).thenReturn(true);

       AccountException orderException
               =  assertThrows(AccountException.class, ()->  signUpApplication.customerSignUp(customerSignUpForm));
        assertEquals(ALREADY_REGISTER_USER, orderException.getErrorCode());
    }



}