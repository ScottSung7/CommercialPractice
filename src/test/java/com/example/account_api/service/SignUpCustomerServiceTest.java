package com.example.account_api.service;

import com.example.account_api.domain.model.Customer;
import com.example.account_api.repository.customer.CustomerJPARepository;
import com.example.account_api.repository.customer.CustomerRepository;
import com.example.account_api.web.TestConfiguration;
import com.example.account_api.web.Tester;
import com.example.account_api.web.validation.form.SignUpCustomerForm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

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

}