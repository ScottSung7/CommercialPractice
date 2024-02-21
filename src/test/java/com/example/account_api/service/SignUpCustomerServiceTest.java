package com.example.account_api.service;

import com.example.account_api.application.service.SignUpCustomerService;
import com.example.account_api.domain.model.Customer;
import com.example.account_api.repository.customer.CustomerRepository;
import com.example.account_api.web.TestConfiguration;
import com.example.account_api.web.Tester;
import com.example.account_api.web.validation.form.SignUpCustomerForm;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@Import(TestConfiguration.class)
class SignUpCustomerServiceTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private SignUpCustomerService signUpCustomerService;

    @Test
    void signUpService_working(){
        SignUpCustomerForm signUpCustomerForm = Tester.signUpCustomerForm;
        Customer customer = Customer.from(signUpCustomerForm);

        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        Customer customerReturned = signUpCustomerService.signUp(signUpCustomerForm);

        assertEquals(customerReturned.getName(), "tester");
    }
    @Test
    void isEmailExist_test(){
        SignUpCustomerForm signUpCustomerForm = Tester.signUpCustomerForm;
        Customer customer = Customer.from(signUpCustomerForm);

        when(customerRepository.findByEmail(signUpCustomerForm.getEmail())).thenReturn(Optional.ofNullable(customer));

        Optional<Customer> customerOptional = signUpCustomerService.isEmailExist(signUpCustomerForm.getEmail());

        assertEquals(customerOptional.isPresent(), true);
    }

}