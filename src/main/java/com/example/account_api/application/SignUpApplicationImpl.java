package com.example.account_api.application;

import com.example.account_api.application.provider.emailVerification.EmailVerificationProvider;
import com.example.account_api.application.service.signIn.SignUpCustomerService;
import com.example.account_api.domain.model.Customer;
import com.example.account_api.web.validation.exception.AccountException;
import com.example.account_api.web.validation.form.customer.SignUpCustomerForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.example.account_api.web.validation.exception.ErrorCode.ALREADY_REGISTER_USER;

@Service
@Slf4j
@RequiredArgsConstructor
public class SignUpApplicationImpl implements SignUpApplication {

    private final SignUpCustomerService signUpCustomerService;
    private final EmailVerificationProvider emailVerificationProvider;

    public String customerSignUp(SignUpCustomerForm signUpCustomerForm){

        if(signUpCustomerService.isEmailExist(signUpCustomerForm.getEmail())){
          throw new AccountException(ALREADY_REGISTER_USER);
        };
        Customer signUpCustomer = signUpCustomerService.signUp(signUpCustomerForm);

//        String verificationCode = emailVerificationProvider.sendVerificationEmail(signUpCustomer);
//        signUpCustomerService.changeCustomerValidateEmail(signUpCustomer, verificationCode);

        return "회원가입에 성공하였습니다.";
    }




}
