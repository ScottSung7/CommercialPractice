package com.example;

import com.example.config.SpringSecurity.CorsConfig;
import com.example.config.SpringSecurity.CustomerSecurityConfiguration_Session;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootApplication
public class MainApplication {


    public static void main(String[] args) {

        SpringApplication.run(MainApplication.class, args);
    }

}
