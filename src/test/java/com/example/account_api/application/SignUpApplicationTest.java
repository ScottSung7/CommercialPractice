package com.example.account_api.application;

import com.example.account_api.domain.model.Customer;
import com.example.account_api.service.SignUpCustomerService;
import com.example.account_api.web.TestConfiguration;
import com.example.account_api.web.Tester;
import com.example.account_api.web.validation.form.SignUpCustomerForm;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@Import(TestConfiguration.class)
class SignUpApplicationTest {

    @Autowired
    private SignUpCustomerService signUpCustomerService;

    @Autowired
    private SignUpApplication signUpApplication;
    @Test
    @DisplayName("signUpCustomerService_작동중.")
    void signUpApplication_working() {
        SignUpCustomerForm signUpCustomerForm = Tester.signUpCustomerForm;
        Customer customer = Customer.from(signUpCustomerForm);

        when(signUpCustomerService.signUp(signUpCustomerForm)).thenReturn(customer); // 수정된 부분

        System.out.println(signUpApplication.customerSignUp(signUpCustomerForm));
    }
}