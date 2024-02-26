package com.example.account_api.application;

import com.example.account_api.application.provider.emailVerification.EmailVerificationProvider;
import com.example.account_api.domain.model.Customer;
import com.example.account_api.application.service.signIn.SignUpCustomerService;
import com.example.account_api.web.Tester;
import com.example.account_api.web.validation.exception.AccountException;
import com.example.account_api.web.validation.exception.ErrorCode;
import com.example.account_api.web.validation.form.customer.SignUpCustomerForm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
    private EmailVerificationProvider emailVerificationProvider;

    private SignUpApplication signUpApplication;

    @BeforeEach
    public void beforeEach(){
        signUpApplication = new SignUpApplicationImpl(signUpCustomerService, emailVerificationProvider);
    }

    @Test
    @DisplayName("signUpCustomerApplication_작동중.")
    void signUpApplication_working_mock() {
        SignUpCustomerForm signUpCustomerForm = Tester.signUpCustomerForm;
        Customer customer = Customer.from(signUpCustomerForm);

        when(signUpCustomerService.isEmailExist(signUpCustomerForm.getEmail())).thenReturn(false);
        when(signUpCustomerService.signUp(signUpCustomerForm)).thenReturn(customer);

        //when(emailVerificationProvider.sendVerificationEmail(customer)).thenReturn("Code");
        //when(signUpCustomerService.changeCustomerValidateEmail(customer, "Code")).thenReturn(customer);

        System.out.println(signUpApplication.customerSignUp(signUpCustomerForm));
    }

    @Test
    @DisplayName("signUpCustomerService ALREADY_REGISTER_EXCEPTION.")
    void signUpApplication_alreadyRegisterException() {
        SignUpCustomerForm signUpCustomerForm = Tester.signUpCustomerForm;
        Customer customer = Customer.from(signUpCustomerForm);

        when(signUpCustomerService.isEmailExist(signUpCustomerForm.getEmail())).thenReturn(true);

       AccountException accountException
               =  assertThrows(AccountException.class, ()->  signUpApplication.customerSignUp(signUpCustomerForm));
        assertEquals(ErrorCode.ALREADY_REGISTER_USER, accountException.getErrorCode());
    }


}