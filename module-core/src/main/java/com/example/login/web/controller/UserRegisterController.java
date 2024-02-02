package com.example.login.web.controller;

import com.example.login.domain.Users;
import com.example.login.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserRegisterController {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    String roleUser = "ROLE_USER";
    String roleAdmin = "ROLE_ADMIN";

    @PostMapping("/join/user")
    public String joinUser(@RequestBody Users user) {

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles(roleUser);
        System.out.println(user.getUsername());
        System.out.println(user.getPassword());

        userRepository.save(user);

        return "유저 회원가입완료";
    }

    @PostMapping("/join/admin")
    public String joinAdmin(@RequestBody Users user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles(roleAdmin);
        userRepository.save(user);
        return "어드민 회원가입완료";
    }

}
