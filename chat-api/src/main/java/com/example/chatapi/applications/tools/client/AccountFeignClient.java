package com.example.chatapi.applications.tools.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "account-api")
public interface AccountFeignClient {
    @PostMapping("/accountChecking")
    ResponseEntity<String> accountTest(@RequestHeader("Authorization") String token);

    @PostMapping("/accounts/customer/info")
    ResponseEntity<?> getCustomerInfoForChat(@RequestParam("email") String email, @RequestHeader("Authorization") String token);

    @PostMapping("/accounts/seller/info")
    HttpEntity<?> getSellerInfoForChat(@RequestParam("email")String email, @RequestHeader("Authorization") String token);
    @PostMapping("/accounts/user/info")
    HttpEntity<?> getUserInfoForChat(@RequestParam("email") String email, @RequestHeader("Authorization") String token);
}
