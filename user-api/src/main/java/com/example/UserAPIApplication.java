package com.example;


import org.springdoc.webmvc.ui.SwaggerConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableFeignClients
@ImportAutoConfiguration({FeignAutoConfiguration.class})
@EnableJpaAuditing
@EnableJpaRepositories
@SpringBootApplication
public class UserAPIApplication {

    public static void main(String[] args) {

        SpringApplication.run(UserAPIApplication.class, args);

    }
}
