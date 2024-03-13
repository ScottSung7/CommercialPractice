package com.example.accountapi.application.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "order-api", url="localhost:8081")
public interface OrderServiceClient {
    @PostMapping("/orderFeignTesting")
    ResponseEntity<String> orderTesting();
}
