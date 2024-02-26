package com.example.account_api.web.controller.account;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.example.account_api.web.controller.account.AccountAPIControllerProperties.*;

@RestController
@RequestMapping(ACCOUNT_COMMON_URL)
public class LogInController {

    @PostMapping(CUSTOMER_LOGIN)
    public ResponseEntity<?> customerLogin(){

        return null;
    }
    @GetMapping(CUSTOMER_VERIFY)
    public ResponseEntity<?> customerVerify(){
        return null;
    }

    @PostMapping(SELLER_LOGIN)
    public ResponseEntity<?> sellerLogin(){
        return null;
    }
    @GetMapping(SELLER_VERIFY)
    public ResponseEntity<?> sellerVerify(){
        return null;
    }


}
