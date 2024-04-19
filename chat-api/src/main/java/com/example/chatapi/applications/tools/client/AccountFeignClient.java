package com.example.chatapi.applications.tools.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "account-api")
public interface AccountFeignClient {
    @PostMapping("/accountChecking")
    ResponseEntity<String> accountTest();
}
