package com.example.account_api.service;

import com.example.account_api.application.service.signIn.SignUpCustomerService;
import com.example.account_api.application.service.signIn.SignUpCustomerServiceImpl;
import com.example.account_api.domain.model.Customer;
import com.example.account_api.repository.customer.CustomerRepository;
import com.example.account_api.web.Tester;
import com.example.account_api.web.validation.form.customer.SignUpCustomerForm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SignUpCustomerServiceTest_Mock {

    @Mock
    private CustomerRepository customerRepository;

    private SignUpCustomerService signUpCustomerService;

    @BeforeEach
    public void beforeEach(){
        signUpCustomerService = new SignUpCustomerServiceImpl(customerRepository);
    }

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

        assertTrue(signUpCustomerService.isEmailExist(signUpCustomerForm.getEmail()));
    }

}