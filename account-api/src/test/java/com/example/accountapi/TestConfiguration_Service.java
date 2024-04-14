package com.example.accountapi;


import com.example.accountapi.application.applications.signUp.SignUpApplication;
import com.example.accountapi.application.applications.signUp.SignUpApplicationImpl;
import com.example.accountapi.application.tools.provider.emailVerification.EmailVerificationProvider;
import com.example.accountapi.application.service.signUp.customer.SignUpCustomerService;
import com.example.accountapi.application.service.signUp.customer.SignUpCustomerServiceImpl;
import com.example.accountapi.application.service.signUp.seller.SignUpSellerService;
import com.example.accountapi.application.service.signUp.seller.SignUpSellerServiceImpl;
import com.example.accountapi.repository.customer.CustomerRepository;
import com.example.accountapi.repository.seller.SellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.ArgumentMatchers.any;

@TestConfiguration
public class TestConfiguration_Service {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private SellerRepository sellerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
;
    @MockBean
    private EmailVerificationProvider emailVerificationProvider;

    @Bean
    public SignUpApplication signUpApplication() {
        // when(emailVerificationProvider.sendVerificationEmail(any(Customer.class))).thenReturn(true);
        return new SignUpApplicationImpl(signUpCustomerService(), signUpSellerService(), emailVerificationProvider);
    }

    @Bean
    public SignUpCustomerService signUpCustomerService() {
        return new SignUpCustomerServiceImpl(customerRepository, passwordEncoder);
    }

    @Bean
    public SignUpSellerService signUpSellerService() {
        return new SignUpSellerServiceImpl(sellerRepository, passwordEncoder);
    }



}
