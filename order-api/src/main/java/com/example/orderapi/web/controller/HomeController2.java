package com.example.orderapi.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HomeController2 {

 @GetMapping("/testorder")
    public String testOrder(){
        return "testorder";
 }

 @PostMapping("/orderFeignTesting")
    public ResponseEntity<String> testOrder2(){
        return ResponseEntity.ok("This is feign Test");
 }

}
