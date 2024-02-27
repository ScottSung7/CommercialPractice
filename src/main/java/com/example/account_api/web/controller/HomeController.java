package com.example.account_api.web.controller;

import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
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

    @GetMapping("/customer/login")
    public String loginCustomer(){
        return "LogInCustomer";
    }
    @GetMapping("/seller/login")
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

    @RequestMapping("/test")
        public String test(){
        return "checking";
    }

    }
