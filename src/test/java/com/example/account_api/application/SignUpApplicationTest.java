package com.example.account_api.application;

import com.example.account_api.domain.model.Customer;
import com.example.account_api.application.service.SignUpCustomerService;
import com.example.account_api.repository.customer.CustomerRepository;
import com.example.account_api.web.TestConfiguration;
import com.example.account_api.web.Tester;
import com.example.account_api.web.validation.exception.AccountException;
import com.example.account_api.web.validation.exception.ErrorCode;
import com.example.account_api.web.validation.form.SignUpCustomerForm;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.*;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@Import({TestConfiguration.class})
class SignUpApplicationTest {

    @Autowired
    private SignUpCustomerService signUpCustomerService;

    @Autowired
    private SignUpApplication signUpApplication;
    @Test
    @DisplayName("signUpCustomerService_작동중.")
    void signUpApplication_working() {
        SignUpCustomerForm signUpCustomerForm = Tester.signUpCustomerForm;
        Customer customer = Customer.from(signUpCustomerForm);

        when(signUpCustomerService.isEmailExist(signUpCustomerForm.getEmail())).thenReturn(Optional.of(customer));
        when(signUpCustomerService.signUp(signUpCustomerForm)).thenReturn(customer);

        System.out.println(signUpApplication.customerSignUp(signUpCustomerForm));
    }

    @Test
    @DisplayName("signUpCustomerService 에러.")
    void signUpApplication_error() {
        SignUpCustomerForm signUpCustomerForm = Tester.signUpCustomerForm;
        Customer customer = Customer.from(signUpCustomerForm);

        when(signUpCustomerService.isEmailExist(signUpCustomerForm.getEmail())).thenReturn(Optional.ofNullable(null));
        when(signUpCustomerService.signUp(signUpCustomerForm)).thenReturn(customer);

       AccountException accountException
               =  assertThrows(AccountException.class, ()->  signUpApplication.customerSignUp(signUpCustomerForm));
        assertEquals(ErrorCode.ALREADY_REGISTER_USER, accountException.getErrorCode());
    }


}