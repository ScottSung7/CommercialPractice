package com.example.login.web.controller;

import com.example.login.config.auth.PrincipalDetails;
import com.example.login.domain.Users;
import com.example.login.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class LoginController {

//    private final PasswordEncoder encoder;
//    private final UserRepository userRepository;
//    @PostMapping("/token")
//    public String token(){
//        System.out.println("token");
//        return "token";
//    }
//
//    @GetMapping("/dashboard")
//    public String dashboard(Model model, Principal principal) {
//        System.out.println("dashboard");
//        return "dashboard";
//    }
//
//    @ResponseBody
//    @PostMapping("/join")
//    public Users join(@RequestBody Users users) {
//        users.setPassword(encoder.encode(users.getPassword()));
//        users.setRoles("USER");
//        return userRepository.save(users);
//
//    }
//
//    @GetMapping("/user")
//    public String user(){
//        return "save";
//    }
//
//    @GetMapping("/admin")
//    public String admin(Model model, Principal principal) {
//        return "index";
//    }


    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    // 모든 사람이 접근 가능
    @GetMapping("home")
    public String home() {
        return "<h1>home</h1>";
    }

    // Tip : JWT를 사용하면 UserDetailsService를 호출하지 않기 때문에 @AuthenticationPrincipal 사용
    // 불가능.
    // 왜냐하면 @AuthenticationPrincipal은 UserDetailsService에서 리턴될 때 만들어지기 때문이다.

    // 유저 혹은 매니저 혹은 어드민이 접근 가능
    @GetMapping("user")
    public String user(Authentication authentication) {
        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        System.out.println("principal : " + principal.getUser().getId());
        System.out.println("principal : " + principal.getUser().getUsername());
        System.out.println("principal : " + principal.getUser().getPassword());

        return "<h1>user</h1>";
    }

    // 매니저 혹은 어드민이 접근 가능
    @GetMapping("manager/reports")
    public String reports() {
        return "<h1>reports</h1>";
    }

    // 어드민이 접근 가능
    @GetMapping("admin/users")
    public List<Users> users() {
        return userRepository.findAll();
    }

    @PostMapping("join")
    public String join(@RequestBody Users user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles("ROLE_MANAGER");
        userRepository.save(user);
        return "회원가입완료";
    }


}