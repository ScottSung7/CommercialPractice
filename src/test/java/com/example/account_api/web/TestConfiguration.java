package com.example.account_api.web;

import com.example.account_api.application.applications.signUp.SignUpApplication;
import com.example.account_api.application.applications.signUp.SignUpApplicationImpl;
import com.example.account_api.application.provider.emailVerification.EmailVerificationProvider;

import com.example.account_api.application.service.signIn.customer.SignUpCustomerServiceImpl_SpringSecurity;
import com.example.account_api.application.service.signIn.seller.SignUpSellerService;
import com.example.account_api.domain.model.Customer;
import com.example.account_api.repository.customer.CustomerRepository;
import com.example.account_api.application.service.signIn.customer.SignUpCustomerService;
import com.example.account_api.application.service.signIn.seller.SignUpSellerServiceImpl_SpringSecurity;

import com.example.account_api.repository.seller.SellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@org.springframework.boot.test.context.TestConfiguration
public class TestConfiguration {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private SellerRepository sellerRepository;
    @Autowired
    @Qualifier("customerPasswordEncoder")
    private PasswordEncoder customerPasswordEncoder;

    @Autowired
    @Qualifier("sellerPasswordEncoder")
    private PasswordEncoder sellerPasswordEncoder;

    @MockBean
    private EmailVerificationProvider emailVerificationProvider;

    @Bean
    public SignUpApplication signUpApplication() {
        Customer customer = Customer.from(Tester.signUpCustomerForm);
        when(emailVerificationProvider.sendVerificationEmail(customer)).thenReturn("code");
        return new SignUpApplicationImpl(signUpCustomerService(), signUpSellerService(), emailVerificationProvider);
    }

    @Bean
    public SignUpCustomerService signUpCustomerService() {
        return new SignUpCustomerServiceImpl_SpringSecurity(customerRepository, customerPasswordEncoder);
    }

    @Bean
    public SignUpSellerService signUpSellerService() {
        return new SignUpSellerServiceImpl_SpringSecurity(sellerRepository, sellerPasswordEncoder);
    }



}
