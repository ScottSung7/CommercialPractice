package com.example.chat.controller;

import com.example.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @GetMapping("/chat")
    public String index(){
        return "index2";
    }

    @GetMapping("/chat/test")
    public String testing(){
        chatService.test();
       return "testing";
    }

}

