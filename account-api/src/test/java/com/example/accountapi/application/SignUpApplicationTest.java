package com.example.accountapi.application;


import com.example.accountapi.application.applications.signUp.SignUpApplication;
import com.example.accountapi.domain.model.Customer;
import com.example.accountapi.web.TestConfiguration;
import com.example.accountapi.web.Tester;

import com.example.accountapi.web.validation.exception.AccountException;
import com.example.accountapi.web.validation.form.customer.CustomerSignUpForm;
import com.example.accountapi.web.validation.form.seller.SellerSignUpForm;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;

import static com.example.accountapi.web.validation.exception.ErrorCode.ALREADY_REGISTER_USER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

//@SpringBootTest
//@Import({TestConfiguration.class})

@SpringBootTest
@Import(TestConfiguration.class)
@Disabled
class SignUpApplicationTest {


    @Autowired
    private ApplicationContext applicationContext;

    @Test
    public void contextLoads() throws Exception {
        if (applicationContext != null) {
            String[] beans = applicationContext.getBeanDefinitionNames();

            for (String bean : beans) {
                System.out.println("bean : " + bean);
            }
        }
    }


    @Autowired
    private SignUpApplication signUpApplication;
    //EmailVerification은 TestConfiguration에서 Mock처리.
    @Test
    @DisplayName("SignUpApplication : signUp_Customer")
    void signUp_Customer() {
        CustomerSignUpForm customerSignUpForm = Tester.customerSignUpForm;
        System.out.println(signUpApplication.customerSignUp(customerSignUpForm));
    }
    @Test
    @Transactional
    @DisplayName("Update")
    void update_Customer(){

//        UpdateCustomerForm updateCustomerForm = Tester.updateCustomerForm;
//        Customer customer = signUpApplication.customerUpdate(updateCustomerForm, "sungscott@naver.com");
//
//        System.out.println(customer);
    }
    @Test
    @DisplayName("SignUpApplication : signUp_Seller")
    void signUp_Seller() {
        SellerSignUpForm sellerSignUpForm = Tester.sellerSignUpForm;
        System.out.println(signUpApplication.sellerSignUp(sellerSignUpForm));
    }

    
    @Test
    @DisplayName("signUpApplication ALREADY_REGISTER_EXCEPTION.")
    @Disabled
    void signUpApplication_alreadyRegisterException() {
        CustomerSignUpForm customerSignUpForm = Tester.customerSignUpForm;
        Customer customer = Customer.from(customerSignUpForm);

        signUpApplication.customerSignUp(customerSignUpForm);

       AccountException orderException
               =  assertThrows(AccountException.class, ()->  signUpApplication.customerSignUp(customerSignUpForm));
        assertEquals(ALREADY_REGISTER_USER, orderException.getErrorCode());
    }


}