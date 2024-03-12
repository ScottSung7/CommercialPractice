package com.example.accountapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
@Profile({"dev", "prod"})
public class AccountApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(AccountApiApplication.class, args);
    }

}
