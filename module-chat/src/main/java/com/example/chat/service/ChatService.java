package com.example.chat.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
@Service
public class ChatService {
    public String test() {
        System.out.println("TTTTEST");

        URI uri = UriComponentsBuilder
                .fromUriString("http://localhost:8080")
                .path("/chat/record")
                .encode()
                .build()
                .toUri();

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(uri, String.class);
        System.out.println("body: "+ responseEntity.getBody());

        return responseEntity.getBody();
    }
}
