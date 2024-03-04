package com.example.account_api.application;

import com.example.account_api.application.applications.signUp.SignUpApplication;
import com.example.account_api.application.applications.signUp.SignUpApplicationImpl;
import com.example.account_api.application.provider.emailVerification.EmailVerificationProvider;
import com.example.account_api.application.service.signIn.seller.SignUpSellerService;
import com.example.account_api.domain.model.Customer;
import com.example.account_api.application.service.signIn.customer.SignUpCustomerService;
import com.example.account_api.domain.model.Seller;
import com.example.account_api.web.Tester;
import com.example.account_api.web.validation.exception.AccountException;
import com.example.account_api.web.validation.form.customer.SignUpCustomerForm;
import com.example.account_api.web.validation.form.seller.SignUpSellerForm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.example.account_api.web.validation.exception.ErrorCode.ALREADY_REGISTER_USER;
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
        SignUpCustomerForm signUpCustomerForm = Tester.signUpCustomerForm;
        Customer customer = Customer.from(signUpCustomerForm);

        when(signUpCustomerService.isEmailExist(signUpCustomerForm.getEmail())).thenReturn(false);
        when(signUpCustomerService.signUp(signUpCustomerForm)).thenReturn(customer);

        //when(emailVerificationProvider.sendVerificationEmail(customer)).thenReturn("Code");
        //when(signUpCustomerService.changeCustomerValidateEmail(customer, "Code")).thenReturn(customer);

        System.out.println(signUpApplication.customerSignUp(signUpCustomerForm));
    }

    @Test
    @DisplayName("SignUpApplication Mock: signUp_Seller")
    void signUp_Seller() {
        SignUpSellerForm signUpSellerForm = Tester.signUpSellerForm;
        Seller seller = Seller.from(signUpSellerForm);

        when(signUpSellerService.isEmailExist(signUpSellerForm.getEmail())).thenReturn(false);
        when(signUpSellerService.signUp(signUpSellerForm)).thenReturn(seller);

        //when(emailVerificationProvider.sendVerificationEmail(seller)).thenReturn("Code");
        //when(signUpSellerService.changeSellerValidateEmail(seller, "Code")).thenReturn(seller);

        System.out.println(signUpApplication.sellerSignUp(signUpSellerForm));
    }


    @Test
    @DisplayName("signUpCustomerService ALREADY_REGISTER_EXCEPTION.")
    void signUpApplication_alreadyRegisterException() {
        SignUpCustomerForm signUpCustomerForm = Tester.signUpCustomerForm;
        Customer customer = Customer.from(signUpCustomerForm);

        when(signUpCustomerService.isEmailExist(signUpCustomerForm.getEmail())).thenReturn(true);

       AccountException orderException
               =  assertThrows(AccountException.class, ()->  signUpApplication.customerSignUp(signUpCustomerForm));
        assertEquals(ALREADY_REGISTER_USER, orderException.getErrorCode());
    }



}