package com.example.accountapi.service.customer;


import com.example.accountapi.application.service.signUp.customer.SignUpCustomerService;
import com.example.accountapi.application.service.signUp.customer.SignUpCustomerServiceImpl_SpringSecurity;
import com.example.accountapi.domain.model.Customer;
import com.example.accountapi.repository.customer.CustomerRepository;
import com.example.accountapi.web.Tester;

import com.example.accountapi.web.validation.form.customer.CustomerSignUpForm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SignUpCustomerServiceTest_Security_Mock {

    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private SignUpCustomerService signUpCustomerService;

    @BeforeEach
    public void beforeEach(){
        signUpCustomerService = new SignUpCustomerServiceImpl_SpringSecurity(customerRepository, bCryptPasswordEncoder);
    }

    @Test
    @DisplayName("SignUpCustomerService Mock: signUp_Customer")
    void signUp_Customer(){
        CustomerSignUpForm customerSignUpForm = Tester.customerSignUpForm;
        Customer customer = Customer.from(customerSignUpForm);

        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        Customer customerReturned = signUpCustomerService.signUp(customerSignUpForm);

        assertEquals(customerReturned.getName(), "tester");
    }
    @Test
    @DisplayName("SignUpCustomerService Mock: isEmailExist_Customer")
    void isEmailExist_Customer(){
        CustomerSignUpForm customerSignUpForm = Tester.customerSignUpForm;
        Customer customer = Customer.from(customerSignUpForm);

        when(customerRepository.findByEmail(customerSignUpForm.getEmail())).thenReturn(Optional.ofNullable(customer));

        assertTrue(signUpCustomerService.isEmailExist(customerSignUpForm.getEmail()));
    }

}