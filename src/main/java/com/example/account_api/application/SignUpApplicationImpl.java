package com.example.account_api.application;

import com.example.account_api.service.SignUpCustomerService;
import com.example.account_api.web.validation.form.SignUpCustomerForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SignUpApplicationImpl implements SignUpApplication {

    private final SignUpCustomerService signUpCustomerService;

    public String customerSignUp(SignUpCustomerForm signUpCustomerForm){


        signUpCustomerService.signUp(signUpCustomerForm);

        return "회원가입에 성공하였습니다.";
    }
}
