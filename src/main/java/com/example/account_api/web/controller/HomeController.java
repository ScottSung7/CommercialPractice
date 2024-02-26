package com.example.account_api.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

@RequestMapping("/main")
    public String home(){
    System.out.println("hi");
        return "home";
}

    @RequestMapping("/")
    public String home2(){
        System.out.println("hi");
        return "home";
    }
@GetMapping("/accounts/checking")
    public String check(){
    return "checking";
}

@PostMapping("/accounts/customer/hi2")
    public String hi2(){
    return "checking";
}

@RequestMapping("/test")
    public String test(){
    return "checking";
}

}
