package com.example.account_api.web.controller;

import com.example.account_api.application.SignUpApplication;
import com.example.account_api.web.validation.exception.AccountException;
import com.example.account_api.web.validation.exception.ErrorCode;
import com.example.account_api.web.validation.form.SignUpCustomerForm;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.example.account_api.web.controller.AccountAPIControllerProperties.*;

@RestController
@RequestMapping(ACCOUNT_COMMON_URL)
@RequiredArgsConstructor
public class SignUpController {

    private final SignUpApplication signUpApplication;

    @PostMapping(CUSTOMER_SIGNUP)
    public ResponseEntity<String> customerSignUp(@Validated @RequestBody SignUpCustomerForm customerSignUpForm){
        return ResponseEntity.ok(signUpApplication.customerSignUp(customerSignUpForm));
    }
    @PostMapping("/hi")
    public String hi(){
        throw new AccountException(ErrorCode.UNKNOWN_ERROR);
    }
    @PostMapping(CUSTOMER_UPDATE)
    public ResponseEntity<?> customerUpdate(){
        return null;
    }

    @PostMapping(SELLER_SIGNUP)
    public ResponseEntity<?> sellerSignUp(){
        return null;
    }

    @PostMapping(SELLER_UPDATE)
    public ResponseEntity sellerUpdate(){
        return null;
    }

}
