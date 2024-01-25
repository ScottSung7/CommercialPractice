package com.example.invite.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class HomeController {

    @GetMapping("/")
    public String index(Principal principal){
        if (principal == null) {
            System.out.println("Please Login");
        }else {
            System.out.println("Hello " + principal.getName());
        }
        return "index";
    }
}
