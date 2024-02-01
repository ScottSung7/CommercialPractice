package com.example.core.controller;

import com.example.login.config.auth.PrincipalDetails;
import com.example.login.domain.Users;
import com.example.login.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;

@Controller
/*@RequiredArgsConstructor*/
public class HomeController {

 /*   private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;*/
    @GetMapping("/")
    public String home(){

        return "index";
    }



}
