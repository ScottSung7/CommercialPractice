package com.example.account_api.application;

import com.example.account_api.domain.model.Customer;
import com.example.account_api.web.TestConfiguration;
import com.example.account_api.web.Tester;
import com.example.account_api.web.validation.exception.AccountException;
import com.example.account_api.web.validation.exception.ErrorCode;
import com.example.account_api.web.validation.form.customer.SignUpCustomerForm;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

//@SpringBootTest
//@Import({TestConfiguration.class})

@SpringBootTest
@Import(TestConfiguration.class)
class SignUpApplicationTest {

    @Autowired
    private SignUpApplication signUpApplication;
    //EmailVerification은 TestConfiguration에서 Mock처리.
    @Test
    @DisplayName("signUpCustomerService_작동중.")
    void signUpApplication_working() {
        SignUpCustomerForm signUpCustomerForm = Tester.signUpCustomerForm;
        System.out.println(signUpApplication.customerSignUp(signUpCustomerForm));
    }

    @Test
    @DisplayName("signUpApplication ALREADY_REGISTER_EXCEPTION.")
    @Disabled
    void signUpApplication_alreadyRegisterException() {
        SignUpCustomerForm signUpCustomerForm = Tester.signUpCustomerForm;
        Customer customer = Customer.from(signUpCustomerForm);

        signUpApplication.customerSignUp(signUpCustomerForm);

       AccountException accountException
               =  assertThrows(AccountException.class, ()->  signUpApplication.customerSignUp(signUpCustomerForm));
        assertEquals(ErrorCode.ALREADY_REGISTER_USER, accountException.getErrorCode());
    }


}