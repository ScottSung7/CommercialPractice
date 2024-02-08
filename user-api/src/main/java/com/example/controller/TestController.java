package com.example.controller;

import com.example.controller.dto.EmailCertificationRequestDto;
import com.example.service.AuthService;
import com.example.service.AuthServiceImplement;
import com.example.service.EmailSendService;
import feign.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestController {

    private final EmailSendService emailSendService;

    private final AuthService authService;

    @GetMapping
    public ResponseEntity<Response> sendTestEmail(){
        return emailSendService.sendMail();
    }

    @PostMapping("/email-certification")
    public void SendEmail(){
        EmailCertificationRequestDto dto = new EmailCertificationRequestDto();
        authService.emailCertification(dto);
    }

    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }


   @GetMapping("/hi")
    public String hi(){
        return "hi";
    }


}
