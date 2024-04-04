package com.example.accountapi.web.controller;

import com.example.accountapi.application.tools.client.OrderServiceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {

    @RequestMapping("/main")
        public String home(){
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
    @GetMapping("/accounts/customer/test")
    public String testCustomer(){
        return "checking";
    }
    @RequestMapping("/test")
        public String test(){
        return "checking";
    }





}

