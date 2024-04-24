package com.example.accountapi.application.service.signUp.seller;

import com.example.accountapi.TestConfiguration_Service_Mock;
import com.example.accountapi.domain.model.Seller;
import com.example.accountapi.repository.seller.SellerRepository;
import com.example.accountapi.web.validation.exception.AccountException;
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
import java.util.Optional;

import static com.example.accountapi.web.validation.exception.ErrorCode.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {TestConfiguration_Service_Mock.class})
@ExtendWith(MockitoExtension.class)
class SignUpSellerServiceTest_Mock {

    @Autowired
    private SignUpSellerService signUpSellerService;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private SellerRepository sellerRepository;

    @Mock
    private SellerSignUpForm sellerSignUpForm;
    @Mock
    private Seller sellerMock;

    String signUpEmail= "tester@test.com";      String signUpName = "tester";
    String signUpPassword = "1234";             String signUpPhone = "01000000000";
    LocalDate signUpBirth = LocalDate.of(1990, 01, 01);
    @Test
    void signUp() {
        //given
        given(sellerSignUpForm.getEmail()).willReturn(signUpEmail);
        given(sellerSignUpForm.getName()).willReturn(signUpName);
        given(sellerSignUpForm.getPassword()).willReturn(signUpPassword);
        given(sellerSignUpForm.getPhone()).willReturn(signUpPhone);
        given(sellerSignUpForm.getBirth()).willReturn(signUpBirth);

        //when
        when(sellerRepository.save(any(Seller.class))).thenAnswer(invocation -> invocation.getArgument(0));
        Seller seller = signUpSellerService.signUp(sellerSignUpForm);

        //then
        assertTrue(seller.getEmail().equals(signUpEmail));
        assertTrue(seller.getName().equals(signUpName));
        assertTrue(encoder.matches(signUpPassword, seller.getPassword()));
        assertTrue(seller.getPhone().equals(signUpPhone));
        assertTrue(seller.getBirth().equals(signUpBirth));
    }
    @Test
    void signUp_AlreadyExist() {//내부에서 Customer 생성
        //given
        given(sellerSignUpForm.getEmail()).willReturn(signUpEmail);
        given(sellerSignUpForm.getName()).willReturn(signUpName);
        given(sellerSignUpForm.getPassword()).willReturn(signUpPassword);
        given(sellerSignUpForm.getPhone()).willReturn(signUpPhone);
        given(sellerSignUpForm.getBirth()).willReturn(signUpBirth);

        //when
        when(sellerRepository.findByEmail(signUpEmail)).thenReturn(Optional.of(sellerMock));
        AccountException exception
                =  assertThrows(AccountException.class, ()->  signUpSellerService.signUp(sellerSignUpForm));
        //then
        assertEquals(ALREADY_REGISTER_USER, exception.getErrorCode());
    }
    Long signUpId = 1L;
    @Test
    void addExpirationDate() {
        //NOTE: 하루가 넘어가는 떄이면 에러가 날 수 있다.
        //given
        LocalDateTime now = LocalDateTime.now();
        Seller seller = Seller.builder().id(signUpId).verificationExpiredAt(now).build();
        //when
        when(sellerRepository.findById(signUpId)).thenReturn(Optional.of(seller));
        Seller sellerReturned = signUpSellerService.addExpirationDate(seller, 1);
        //then
        assertTrue(sellerReturned.getVerificationExpiredAt().getDayOfYear() == now.plusDays(1).getDayOfYear());
    }
    @Test
    void addExpirationDate_NOF_FOUND_USER(){
        //given
        Long notExistId = 0L;
        //when
        AccountException exception
                =  assertThrows(AccountException.class, ()->  signUpSellerService.addExpirationDate(sellerMock, 1));
        //then
        assertEquals(NOT_FOUND_USER, exception.getErrorCode());
    }
    @Test
    void sellerVerify() {
        //given
        String email = signUpEmail;
        Seller seller = Seller.builder()
                .email(email)
                .verificationExpiredAt(LocalDateTime.now().plusDays(1))
                .build();
        //when
        when(sellerRepository.findByEmail(email)).thenReturn(Optional.of(seller));
        boolean check = signUpSellerService.sellerVerify(email);
        //then
        assertTrue(check);
    }
    @Test
    void sellerVerify_NOT_FOUND(){
        //given
        String notExistEmail = "";
        //when
        AccountException exception
                =  assertThrows(AccountException.class, ()->  signUpSellerService.sellerVerify(notExistEmail));
        //then
        assertEquals(NOT_FOUND_USER, exception.getErrorCode());
    }
    @Test
    void sellerVerify_ALREADY_VERIFIED(){
        //given
        String email = signUpEmail;
        //when
        when(sellerMock.isVerified()).thenReturn(true);
        when(sellerRepository.findByEmail(email)).thenReturn(Optional.of(sellerMock));
        AccountException exception
                =  assertThrows(AccountException.class, ()->  signUpSellerService.sellerVerify(email));
        //then
        assertEquals(ALREADY_VERIFIED, exception.getErrorCode());
    }
    @Test
    void sellerVerify_VerificationExpired(){
        //given
        String email = signUpEmail;
        //when
        when(sellerMock.getVerificationExpiredAt()).thenReturn(LocalDateTime.now().minusDays(1));
        when(sellerRepository.findByEmail(email)).thenReturn(Optional.of(sellerMock));
        AccountException exception
                =  assertThrows(AccountException.class, ()->  signUpSellerService.sellerVerify(email));
        //then
        assertEquals(VERIFICATION_EXPIRED, exception.getErrorCode());
    }
    @Mock
    private SellerUpdateForm sellerUpdateForm;

    String updatedEmail = "tester@test.com";    String updatedName = "changedTester";
    String updatedPassword = "1235";            String updatedPhone = "0000000";
    LocalDate updatedBirth = LocalDate.of(1990, 01, 01);
    String updatedCompanyRegistrationNumber = "AA211112";

    @Test
    void update() {
        //given
        given(sellerUpdateForm.getEmail()).willReturn(updatedEmail);
        given(sellerUpdateForm.getName()).willReturn(updatedName);
        given(sellerUpdateForm.getPassword()).willReturn(updatedPassword);
        given(sellerUpdateForm.getPhone()).willReturn(updatedPhone);
        given(sellerUpdateForm.getBirth()).willReturn(updatedBirth);
        given(sellerUpdateForm.getCompanyRegistrationNumber()).willReturn(updatedCompanyRegistrationNumber);

        Seller seller = Seller.builder().email(signUpEmail).build();
        
        //when
        when(sellerRepository.findByEmail(updatedEmail)).thenReturn(Optional.of(seller));
        Seller sellerReturned = signUpSellerService.update(sellerUpdateForm); 

        //then
        assertTrue(sellerReturned.getEmail().equals(updatedEmail));
        assertTrue(sellerReturned.getName().equals(updatedName));
        assertTrue(encoder.matches(updatedPassword, sellerReturned.getPassword()));
        assertTrue(sellerReturned.getPhone().equals(updatedPhone));
        assertTrue(sellerReturned.getBirth().equals(updatedBirth));
        assertTrue(sellerReturned.getCompanyRegistrationNumber().equals(updatedCompanyRegistrationNumber));
    }
    @Test
    void update_NOT_FOUND(){
        //given
        given(sellerUpdateForm.getEmail()).willReturn("");
        //when
        AccountException exception
                =  assertThrows(AccountException.class, ()->  signUpSellerService.update(sellerUpdateForm));
        //then
        assertEquals(NOT_FOUND_USER, exception.getErrorCode());
    }
    @Test
    void isEmailExist() {
        //given
        String email = signUpEmail;
        //when
        when(sellerRepository.findByEmail(email)).thenReturn(Optional.of(sellerMock));
        Boolean check = signUpSellerService.isEmailExist(email);
        //then
        assertTrue(check);
    }


}