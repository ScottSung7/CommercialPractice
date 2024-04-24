package com.example.accountapi;



import com.example.accountapi.application.applications.AccountInfo.AccountInfoApplication;
import com.example.accountapi.application.applications.AccountInfo.AccountInfoApplicationImpl;
import com.example.accountapi.application.applications.signUp.SignUpApplication;
import com.example.accountapi.application.applications.signUp.SignUpApplicationImpl;
import com.example.accountapi.application.service.accountInfo.customer.AccountInfoCustomerService;
import com.example.accountapi.application.service.accountInfo.customer.AccountInfoCustomerServiceImpl;
import com.example.accountapi.application.service.accountInfo.seller.AccountInfoSellerService;
import com.example.accountapi.application.service.accountInfo.seller.AccountInfoSellerServiceImpl;
import com.example.accountapi.application.service.signUp.customer.SignUpCustomerService;
import com.example.accountapi.application.service.signUp.customer.SignUpCustomerServiceImpl;
import com.example.accountapi.application.service.signUp.seller.SignUpSellerService;
import com.example.accountapi.application.service.signUp.seller.SignUpSellerServiceImpl;
import com.example.accountapi.application.tools.provider.emailVerification.EmailVerificationProvider;
import com.example.accountapi.repository.customer.CustomerRepository;
import com.example.accountapi.repository.seller.SellerRepository;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@TestConfiguration
public class TestConfiguration_Application_Mock {


    @Autowired
    private PasswordEncoder passwordEncoder;
;
    @MockBean
    private EmailVerificationProvider emailVerificationProvider;
    
    @MockBean
    private SignUpCustomerService signUpCustomerService;
    @MockBean
    private SignUpSellerService signUpSellerService;

    @Bean
    public SignUpApplication signUpApplication() {
        return new SignUpApplicationImpl(signUpCustomerService, signUpSellerService, emailVerificationProvider);
    }

//    @Bean
//    public SignUpCustomerService signUpCustomerService() {
//        return new SignUpCustomerServiceImpl(customerRepository, passwordEncoder);
//    }
//
//    @Bean
//    public SignUpSellerService signUpSellerService() {
//        return new SignUpSellerServiceImpl(sellerRepository, passwordEncoder);
//    }
//
//    @Bean
//    public AccountInfoApplication accountInfoApplication() {
//        return new AccountInfoApplicationImpl(accountInfoCustomerService(), accountInfoSellerService());
//    }
//    @Bean
//    public AccountInfoCustomerService accountInfoCustomerService() {
//        return new AccountInfoCustomerServiceImpl(customerRepository);
//    }
//    @Bean
//    public AccountInfoSellerService accountInfoSellerService(){
//        return new AccountInfoSellerServiceImpl(sellerRepository);
//    }

}
