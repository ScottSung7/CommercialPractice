package com.example.accountapi.application.tools.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "order-api")
public interface OrderServiceClient {
    @PostMapping("/orderFeignTesting")
    ResponseEntity<String> orderTesting();
}