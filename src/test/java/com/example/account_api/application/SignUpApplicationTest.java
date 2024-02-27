package com.example.account_api.application;

import com.example.account_api.application.applications.signUp.SignUpApplication;
import com.example.account_api.domain.model.Customer;
import com.example.account_api.web.TestConfiguration;
import com.example.account_api.web.Tester;
import com.example.account_api.web.validation.exception.AccountException;
import com.example.account_api.web.validation.exception.ErrorCode;
import com.example.account_api.web.validation.form.customer.SignUpCustomerForm;
import com.example.account_api.web.validation.form.customer.UpdateCustomerForm;
import com.example.account_api.web.validation.form.seller.SignUpSellerForm;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

//@SpringBootTest
//@Import({TestConfiguration.class})

@SpringBootTest
@Import(TestConfiguration.class)
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
        SignUpCustomerForm signUpCustomerForm = Tester.signUpCustomerForm;
        System.out.println(signUpApplication.customerSignUp(signUpCustomerForm));
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
        SignUpSellerForm signUpSellerForm = Tester.signUpSellerForm;
        System.out.println(signUpApplication.sellerSignUp(signUpSellerForm));
    }

    
    @Test
    @DisplayName("signUpApplication ALREADY_REGISTER_EXCEPTION.")
    @Disabled
    void signUpApplication_alreadyRegisterException() {
        SignUpCustomerForm signUpCustomerForm = Tester.signUpCustomerForm;
        Customer customer = Customer.from(signUpCustomerForm);

        signUpApplication.customerSignUp(signUpCustomerForm);

       AccountException accountException
               =  assertThrows(AccountException.class, ()->  signUpApplication.customerSignUp(signUpCustomerForm));
        assertEquals(ErrorCode.ALREADY_REGISTER_USER, accountException.getErrorCode());
    }


}