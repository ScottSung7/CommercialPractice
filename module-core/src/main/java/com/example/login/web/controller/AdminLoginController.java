package com.example.login.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminLoginController {

    @GetMapping("admin")
    public String admin(){
        return "admin";
    }
}
