package com.example.service;

import com.example.config.FeignConfig;
import feign.Response;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

@SpringBootTest
class EmailSendServiceTest {

    @Autowired
    private EmailSendService emailSendService;


    @Test
    @Disabled
    public void EmailTest(){
        emailSendService.sendMail();
    }

    @Test
    @Disabled
    public void EmailSendingTest(){
        ResponseEntity<Response> response = emailSendService.sendMail();
        System.out.println(response);
    }

}