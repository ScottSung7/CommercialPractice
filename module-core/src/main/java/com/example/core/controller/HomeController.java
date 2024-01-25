package com.example.core.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.security.Principal;
import java.util.HashMap;

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

    @GetMapping("/test")
    public String testing(){
        return "test";
    }

    @PostMapping("/hi")
    public void hi(@RequestBody HashMap<String, String> map){
        System.out.println("hi testing");
        System.out.println(map);
    }
}
