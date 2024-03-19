package com.example.accountapi.web.controller;

import com.example.accountapi.application.client.OrderServiceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {

    @RequestMapping("/main")
        public String home(){
        System.out.println("hi");
            return "home";
    }

    @GetMapping("/accounts/customer/login")
    public String loginCustomer(){
        return "LogInCustomer";
    }
    @GetMapping("/accounts/seller/login")
    public String loginSeller(){
        return "LogInSeller";
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

    @Autowired
    OrderServiceClient orderServiceClient;




}

