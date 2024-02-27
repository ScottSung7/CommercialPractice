package com.example.account_api.service.seller;

import com.example.account_api.application.service.signIn.seller.SignUpSellerService;
import com.example.account_api.domain.model.Seller;
import com.example.account_api.web.Tester;
import com.example.account_api.web.validation.form.seller.SignUpSellerForm;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
class SignUpSellerServiceTest {

    @Autowired
    private SignUpSellerService signUpSellerService;
    @Test
    @DisplayName("SignUpSellerService: signUp_Seller")
    void signUp_Seller(){
        SignUpSellerForm signUpSellerForm = Tester.signUpSellerForm;
        Seller seller = Seller.from(signUpSellerForm);

        Seller sellerReturned = signUpSellerService.signUp(signUpSellerForm);

        assertEquals(sellerReturned.getName(), "tester");
    }
    @Test
    @DisplayName("SignUpSellerService: isEmailExist_Seller")
    void isEmailExist_Seller(){
        SignUpSellerForm signUpSellerForm = Tester.signUpSellerForm;
        Seller seller = Seller.from(signUpSellerForm);

        assertFalse(signUpSellerService.isEmailExist(signUpSellerForm.getEmail()));
    }

}