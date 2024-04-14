package com.example.accountapi.application.service.signUp.seller;

import com.example.accountapi.TestConfiguration_Service;
import com.example.accountapi.domain.model.Seller;
import com.example.accountapi.web.validation.form.seller.SellerSignUpForm;
import com.example.accountapi.web.validation.form.seller.SellerUpdateForm;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@SpringBootTest(classes = {TestConfiguration_Service.class})
@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Disabled
class SignUpSellerServiceTest {

    @Autowired
    private SignUpSellerService signUpSellerService;
    @Autowired
    private PasswordEncoder encoder;

    @Mock
    private SellerSignUpForm sellerSignUpForm;
    String signUpEmail= "tester@test.com";      String signUpName = "tester";
    String signUpPassword = "1234";             String signUpPhone = "01000000000";
    LocalDate signUpBirth = LocalDate.of(1990, 01, 01);
    @Test
    @Order(1)
    void signUp() {
        //given
        given(sellerSignUpForm.getEmail()).willReturn(signUpEmail);
        given(sellerSignUpForm.getName()).willReturn(signUpName);
        given(sellerSignUpForm.getPassword()).willReturn(signUpPassword);
        given(sellerSignUpForm.getPhone()).willReturn(signUpPhone);
        given(sellerSignUpForm.getBirth()).willReturn(signUpBirth);

        //when
        Seller seller = signUpSellerService.signUp(sellerSignUpForm);

        //then
        assertTrue(seller.getEmail().equals(signUpEmail));
        assertTrue(seller.getName().equals(signUpName));
        assertTrue(encoder.matches(signUpPassword, seller.getPassword()));
        assertTrue(seller.getPhone().equals(signUpPhone));
        assertTrue(seller.getBirth().equals(signUpBirth));
    }
    @Mock
    private Seller seller;
    Long signUpId = 1L;
    @Test
    @Order(2)
    void addOneDayForExpirationDate() {
        //NOTE: 하루가 넘어가는 떄이면 에러가 날 수 있다.
        LocalDateTime now = LocalDateTime.now().plusDays(1);
        //given
        given(seller.getId()).willReturn(signUpId);
        //when
        Seller sellerReturned = signUpSellerService.addExpirationDate(seller, 1);
        //then
        assertTrue(sellerReturned.getVerificationExpiredAt().getDayOfYear() == now.getDayOfYear());
    }
    @Test
    @Order(3)
    void sellerVerify() {
        //given
        String email = signUpEmail;
        //when
        boolean check = signUpSellerService.sellerVerify(email);
        //then
        assertTrue(check);
    }
    @Test
    @Order(4)
    void isEmailExist() {
        //given
        String email = signUpEmail;
        //when
        Boolean check = signUpSellerService.isEmailExist(email);
        //then
        assertTrue(check);
    }
    @Mock
    private SellerUpdateForm sellerUpdateForm;

    String updatedEmail = "tester@test.com";    String updatedName = "changedTester";
    String updatedPassword = "1235";            String updatedPhone = "0000000";
    LocalDate updatedBirth = LocalDate.of(1990, 01, 01);
    String updatedCompanyRegistrationNumber = "AA211112";

    @Test
    @Order(5)
    void update() {
        //given
        given(sellerUpdateForm.getEmail()).willReturn(updatedEmail);
        given(sellerUpdateForm.getName()).willReturn(updatedName);
        given(sellerUpdateForm.getPassword()).willReturn(updatedPassword);
        given(sellerUpdateForm.getPhone()).willReturn(updatedPhone);
        given(sellerUpdateForm.getBirth()).willReturn(updatedBirth);
        given(sellerUpdateForm.getCompanyRegistrationNumber()).willReturn(updatedCompanyRegistrationNumber);

        //when
        Seller seller = signUpSellerService.update(sellerUpdateForm);

        //then
        assertTrue(seller.getEmail().equals(updatedEmail));
        assertTrue(seller.getName().equals(updatedName));
        assertTrue(encoder.matches(updatedPassword, seller.getPassword()));
        assertTrue(seller.getPhone().equals(updatedPhone));
        assertTrue(seller.getBirth().equals(updatedBirth));
        assertTrue(seller.getCompanyRegistrationNumber().equals(updatedCompanyRegistrationNumber));
    }



}