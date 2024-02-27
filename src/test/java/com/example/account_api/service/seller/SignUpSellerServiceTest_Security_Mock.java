package com.example.account_api.service.seller;

import com.example.account_api.application.service.signIn.seller.SignUpSellerService;
import com.example.account_api.application.service.signIn.seller.SignUpSellerServiceImpl_SpringSecurity;
import com.example.account_api.application.service.signIn.seller.SignUpSellerService;
import com.example.account_api.domain.model.Seller;
import com.example.account_api.repository.seller.SellerRepository;
import com.example.account_api.repository.seller.SellerRepository;
import com.example.account_api.web.Tester;
import com.example.account_api.web.validation.form.seller.SignUpSellerForm;
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
        SignUpSellerForm signUpSellerForm = Tester.signUpSellerForm;
        Seller seller = Seller.from(signUpSellerForm);

        when(sellerRepository.save(any(Seller.class))).thenReturn(seller);

        Seller sellerReturned = signUpSellerService.signUp(signUpSellerForm);

        assertEquals(sellerReturned.getName(), "tester");
    }
    @Test
    @DisplayName("SignUpSellerService Mock: isEmailExist_Seller")
    void isEmailExist_Seller(){
        SignUpSellerForm signUpSellerForm = Tester.signUpSellerForm;
        Seller seller = Seller.from(signUpSellerForm);

        when(sellerRepository.findByEmail(signUpSellerForm.getEmail())).thenReturn(Optional.ofNullable(seller));

        assertTrue(signUpSellerService.isEmailExist(signUpSellerForm.getEmail()));
    }

}