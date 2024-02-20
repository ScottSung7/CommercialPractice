package com.example.account_api.web;

import com.example.account_api.application.SignUpApplication;
import com.example.account_api.application.SignUpApplicationImpl;
import com.example.account_api.domain.model.Customer;
import com.example.account_api.repository.customer.CustomerJPARepository;
import com.example.account_api.repository.customer.CustomerRepository;
import com.example.account_api.service.SignUpCustomerService;
import com.example.account_api.service.SignUpCustomerServiceImpl;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;

import java.util.List;

@org.springframework.boot.test.context.TestConfiguration
public class TestConfiguration {


    @MockBean
    private CustomerRepository customerRepository;

    @Bean
    public SignUpApplication signUpApplication() {
        return new SignUpApplicationImpl(signUpCustomerService());
    }

    @Bean
    public SignUpCustomerService signUpCustomerService() {
        return new SignUpCustomerServiceImpl(customerRepository);
    }

}
