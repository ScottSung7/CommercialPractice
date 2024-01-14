package com.example.invite.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

public class LoginController {

    @GetMapping("/dashboard")
    public String dashboard(Model model, Principal principal) {
        return "index";
    }

    @GetMapping("/admin")
    public String admin(Model model, Principal principal) {

        return "index";
    }

}