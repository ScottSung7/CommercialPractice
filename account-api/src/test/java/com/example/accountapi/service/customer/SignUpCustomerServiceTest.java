package com.example.accountapi.service.customer;


import com.example.accountapi.application.service.signUp.customer.SignUpCustomerService;
import com.example.accountapi.domain.model.Customer;
import com.example.accountapi.web.Tester;

import com.example.accountapi.web.validation.form.customer.CustomerSignUpForm;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@Disabled
class SignUpCustomerServiceTest {

    @Autowired
    private SignUpCustomerService signUpCustomerService;


    @Test
    void signUpService_working(){
        CustomerSignUpForm customerSignUpForm = Tester.customerSignUpForm;
        Customer customer = Customer.from(customerSignUpForm);

        Customer customerReturned = signUpCustomerService.signUp(customerSignUpForm);

        assertEquals(customerReturned.getName(), "tester");
    }
    @Test
    void isEmailExist_test(){
        CustomerSignUpForm customerSignUpForm = Tester.customerSignUpForm;
        Customer customer = Customer.from(customerSignUpForm);

        assertFalse(signUpCustomerService.isEmailExist(customerSignUpForm.getEmail()));
    }

}