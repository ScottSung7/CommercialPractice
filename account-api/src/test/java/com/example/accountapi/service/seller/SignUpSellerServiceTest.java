package com.example.accountapi.service.seller;


import com.example.accountapi.application.service.signUp.seller.SignUpSellerService;
import com.example.accountapi.domain.model.Seller;
import com.example.accountapi.web.Tester;

import com.example.accountapi.web.validation.form.seller.SellerSignUpForm;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
@Disabled
class SignUpSellerServiceTest {

    @Autowired
    private SignUpSellerService signUpSellerService;
    @Test
    @DisplayName("SignUpSellerService: signUp_Seller")
    void signUp_Seller(){
        SellerSignUpForm sellerSignUpForm = Tester.sellerSignUpForm;
        Seller seller = Seller.from(sellerSignUpForm);

        Seller sellerReturned = signUpSellerService.signUp(sellerSignUpForm);

        assertEquals(sellerReturned.getName(), "tester");
    }
    @Test
    @DisplayName("SignUpSellerService: isEmailExist_Seller")
    void isEmailExist_Seller(){
        SellerSignUpForm sellerSignUpForm = Tester.sellerSignUpForm;
        Seller seller = Seller.from(sellerSignUpForm);

        assertFalse(signUpSellerService.isEmailExist(sellerSignUpForm.getEmail()));
    }

}