package com.example.login.web.controller;

import com.example.login.domain.Users;
import com.example.login.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private final PasswordEncoder encoder;
    private final UserRepository userRepository;
    @PostMapping("/token")
    public String token(){
        System.out.println("token");
        return "token";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model, Principal principal) {
        System.out.println("dashboard");
        return "dashboard";
    }

    @ResponseBody
    @PostMapping("/join")
    public Users join(@RequestBody Users users) {
        users.setPassword(encoder.encode(users.getPassword()));
        users.setRoles("USER");
        return userRepository.save(users);

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