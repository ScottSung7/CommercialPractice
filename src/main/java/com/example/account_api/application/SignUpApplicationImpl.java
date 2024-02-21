package com.example.account_api.application;

import com.example.account_api.application.provider.emailVerification.EmailVerificationProvider;
import com.example.account_api.application.provider.emailVerification.form.SendMailForm;
import com.example.account_api.application.service.SignUpCustomerService;
import com.example.account_api.application.tools.LogAndException;
import com.example.account_api.domain.model.Customer;
import com.example.account_api.web.validation.form.SignUpCustomerForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class SignUpApplicationImpl implements SignUpApplication {

    private final SignUpCustomerService signUpCustomerService;
    private final EmailVerificationProvider emailVerificationProvider;
    private final LogAndException logAndException;
    private String ADMIN_EMAIL = "hgssung@gmail.com";

    public String customerSignUp(SignUpCustomerForm signUpCustomerForm){

        logAndException.emailAlreadyExist(signUpCustomerService.isEmailExist(signUpCustomerForm.getEmail()).isPresent());
        Customer signUpCustomer = signUpCustomerService.signUp(signUpCustomerForm);

//        Optional<SendMailForm> optionalSendMailForm = emailVerificationProvider.sendVerificationEmail(ADMIN_EMAIL, signUpCustomer);
//        logAndException.emailVerificationErrorCheck(optionalSendMailForm.isPresent(), signUpCustomer.getEmail());
//
//        signUpCustomerService.changeCustomerValidateEmail(signUpCustomer.getId(), optionalSendMailForm.get().getCode());

        return "회원가입에 성공하였습니다.";
    }
}
