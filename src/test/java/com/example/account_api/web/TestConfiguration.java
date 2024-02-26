package com.example.account_api.web;

import com.example.account_api.application.SignUpApplication;
import com.example.account_api.application.SignUpApplicationImpl;
import com.example.account_api.application.provider.emailVerification.EmailVerificationProvider;

import com.example.account_api.domain.model.Customer;
import com.example.account_api.repository.customer.CustomerRepository;
import com.example.account_api.application.service.signIn.SignUpCustomerService;
import com.example.account_api.application.service.signIn.SignUpCustomerServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@org.springframework.boot.test.context.TestConfiguration
public class TestConfiguration {

    @Autowired
    private CustomerRepository customerRepository;

    @MockBean
    private EmailVerificationProvider emailVerificationProvider;

    @Bean
    public SignUpApplication signUpApplication() {
        Customer customer = Customer.from(Tester.signUpCustomerForm);
        when(emailVerificationProvider.sendVerificationEmail(customer)).thenReturn("code");
        return new SignUpApplicationImpl(signUpCustomerService(), emailVerificationProvider);
    }

    @Bean
    public SignUpCustomerService signUpCustomerService() {
        return new SignUpCustomerServiceImpl(customerRepository);
    }




}
