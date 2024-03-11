package com.example.accountapi.web.controller.account;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(AccountAPIControllerProperties.ACCOUNT_COMMON_URL)
public class LogInController {

    @PostMapping(AccountAPIControllerProperties.CUSTOMER_LOGIN)
    public ResponseEntity<?> customerLogin(){
        return null;
    }


    @PostMapping(AccountAPIControllerProperties.SELLER_LOGIN)
    public ResponseEntity<?> sellerLogin(){
        return null;
    }



}
