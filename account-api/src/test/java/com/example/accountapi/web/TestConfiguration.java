package com.example.accountapi.web;


import com.example.accountapi.application.applications.signUp.SignUpApplication;
import com.example.accountapi.application.applications.signUp.SignUpApplicationImpl;
import com.example.accountapi.application.tools.provider.emailVerification.EmailVerificationProvider;
import com.example.accountapi.application.service.signUp.customer.SignUpCustomerService;
import com.example.accountapi.application.service.signUp.customer.SignUpCustomerServiceImpl_SpringSecurity;
import com.example.accountapi.application.service.signUp.seller.SignUpSellerService;
import com.example.accountapi.application.service.signUp.seller.SignUpSellerServiceImpl_SpringSecurity;
import com.example.accountapi.domain.model.Customer;
import com.example.accountapi.repository.customer.CustomerRepository;
import com.example.accountapi.repository.seller.SellerRepository;
import org.junit.jupiter.api.Disabled;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;


import static org.mockito.ArgumentMatchers.any;

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
    @Disabled
    public SignUpApplication signUpApplication() {
        Customer customer = Customer.from(Tester.customerSignUpForm);
        //when(emailVerificationProvider.sendVerificationEmail(customer)).thenReturn("code");
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
