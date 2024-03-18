package com.example.accountapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
@Profile({"default", "prod", "docker"})
@EnableFeignClients(basePackages = "com.example.accountapi.application.client")
@ImportAutoConfiguration({FeignAutoConfiguration.class})
public class AccountApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(AccountApiApplication.class, args);
    }

}
