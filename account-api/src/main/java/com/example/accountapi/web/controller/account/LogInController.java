package com.example.accountapi.web.controller.account;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class LogInController {

    @GetMapping("/accounts/customer/login")
    public String loginCustomer(){
        return "LogInCustomer";
    }

    @GetMapping("/accounts/seller/login")
    public String loginSeller(){
        return "LogInSeller";
    }


}
