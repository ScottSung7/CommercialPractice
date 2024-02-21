package com.example.account_api.web;

import com.example.account_api.application.SignUpApplication;
import com.example.account_api.application.SignUpApplicationImpl;
import com.example.account_api.application.provider.emailVerification.EmailVerificationProvider;
import com.example.account_api.application.tools.LogAndException;
import com.example.account_api.domain.model.Customer;
import com.example.account_api.repository.customer.CustomerRepository;
import com.example.account_api.application.service.SignUpCustomerService;
import com.example.account_api.application.service.SignUpCustomerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@org.springframework.boot.test.context.TestConfiguration
public class TestConfiguration {


    @MockBean
    private CustomerRepository customerRepository;

    @MockBean
    private EmailVerificationProvider emailVerificationProvider;

    @Autowired
    private LogAndException logAndException;

    @Bean
    public SignUpApplication signUpApplication() {
        return new SignUpApplicationImpl(signUpCustomerService(), emailVerificationProvider, logAndException);
    }

    @Bean
    public SignUpCustomerService signUpCustomerService() {
        return new SignUpCustomerServiceImpl(customerRepository, logAndException);
    }


}
