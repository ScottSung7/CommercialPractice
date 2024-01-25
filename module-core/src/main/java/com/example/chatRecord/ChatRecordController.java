package com.example.chatRecord;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatRecordController {

    @GetMapping("/chat/record")
    public String chatRecordTest(){
        System.err.println("It is Recording!");
        return "SUCCESS!!";
    }
}
