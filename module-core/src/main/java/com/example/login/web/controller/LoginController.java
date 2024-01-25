package com.example.login.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class LoginController {

    @GetMapping("/dashboard")
    public String dashboard(Model model, Principal principal) {
        return "dashboard";
    }

    @GetMapping("/user")
    public String user(){
        return "save";
    }

    @GetMapping("/admin")
    public String admin(Model model, Principal principal) {
        return "index";
    }

}