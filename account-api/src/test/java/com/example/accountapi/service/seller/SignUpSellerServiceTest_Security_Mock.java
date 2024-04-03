package com.example.accountapi.service.seller;


import com.example.accountapi.application.service.signUp.seller.SignUpSellerService;
import com.example.accountapi.application.service.signUp.seller.SignUpSellerServiceImpl_SpringSecurity;
import com.example.accountapi.domain.model.Seller;
import com.example.accountapi.repository.seller.SellerRepository;
import com.example.accountapi.web.Tester;

import com.example.accountapi.web.validation.form.seller.SellerSignUpForm;
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
class SignUpSellerServiceTest_Security_Mock {

    @Mock
    private SellerRepository sellerRepository;
    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private SignUpSellerService signUpSellerService;
    @BeforeEach
    public void beforeEach(){
        signUpSellerService = new SignUpSellerServiceImpl_SpringSecurity(sellerRepository, bCryptPasswordEncoder);
    }

    @Test
    @DisplayName("SignUpSellerService Mock: signUp_Seller")
    void signUp_Seller(){
        SellerSignUpForm sellerSignUpForm = Tester.sellerSignUpForm;
        Seller seller = Seller.from(sellerSignUpForm);

        when(sellerRepository.save(any(Seller.class))).thenReturn(seller);

        Seller sellerReturned = signUpSellerService.signUp(sellerSignUpForm);

        assertEquals(sellerReturned.getName(), "tester");
    }
    @Test
    @DisplayName("SignUpSellerService Mock: isEmailExist_Seller")
    void isEmailExist_Seller(){
        SellerSignUpForm sellerSignUpForm = Tester.sellerSignUpForm;
        Seller seller = Seller.from(sellerSignUpForm);

        when(sellerRepository.findByEmail(sellerSignUpForm.getEmail())).thenReturn(Optional.ofNullable(seller));

        assertTrue(signUpSellerService.isEmailExist(sellerSignUpForm.getEmail()));
    }

}