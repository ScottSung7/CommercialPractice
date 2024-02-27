package com.example.account_api.service.customer;

import com.example.account_api.application.service.signIn.customer.SignUpCustomerService;
import com.example.account_api.application.service.signIn.customer.SignUpCustomerServiceImpl_SpringSecurity;
import com.example.account_api.application.service.signIn.seller.SignUpSellerServiceImpl_SpringSecurity;
import com.example.account_api.domain.model.Customer;
import com.example.account_api.repository.customer.CustomerRepository;
import com.example.account_api.web.Tester;
import com.example.account_api.web.validation.form.customer.SignUpCustomerForm;
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
        SignUpCustomerForm signUpCustomerForm = Tester.signUpCustomerForm;
        Customer customer = Customer.from(signUpCustomerForm);

        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        Customer customerReturned = signUpCustomerService.signUp(signUpCustomerForm);

        assertEquals(customerReturned.getName(), "tester");
    }
    @Test
    @DisplayName("SignUpCustomerService Mock: isEmailExist_Customer")
    void isEmailExist_Customer(){
        SignUpCustomerForm signUpCustomerForm = Tester.signUpCustomerForm;
        Customer customer = Customer.from(signUpCustomerForm);

        when(customerRepository.findByEmail(signUpCustomerForm.getEmail())).thenReturn(Optional.ofNullable(customer));

        assertTrue(signUpCustomerService.isEmailExist(signUpCustomerForm.getEmail()));
    }

}