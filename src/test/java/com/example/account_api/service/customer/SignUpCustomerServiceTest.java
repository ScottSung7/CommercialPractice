package com.example.account_api.service.customer;

import com.example.account_api.application.service.signIn.customer.SignUpCustomerService;
import com.example.account_api.domain.model.Customer;
import com.example.account_api.web.Tester;
import com.example.account_api.web.validation.form.customer.SignUpCustomerForm;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
class SignUpCustomerServiceTest {

    @Autowired
    private SignUpCustomerService signUpCustomerService;


    @Test
    void signUpService_working(){
        SignUpCustomerForm signUpCustomerForm = Tester.signUpCustomerForm;
        Customer customer = Customer.from(signUpCustomerForm);

        Customer customerReturned = signUpCustomerService.signUp(signUpCustomerForm);

        assertEquals(customerReturned.getName(), "tester");
    }
    @Test
    void isEmailExist_test(){
        SignUpCustomerForm signUpCustomerForm = Tester.signUpCustomerForm;
        Customer customer = Customer.from(signUpCustomerForm);

        assertFalse(signUpCustomerService.isEmailExist(signUpCustomerForm.getEmail()));
    }

}