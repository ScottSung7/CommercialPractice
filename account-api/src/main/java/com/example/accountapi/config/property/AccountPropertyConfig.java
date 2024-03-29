package com.example.accountapi.config.property;

import org.springframework.context.annotation.Configuration;

import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@Configuration
@PropertySources(
        @PropertySource("classpath:env-account-api.properties")
)
public class AccountPropertyConfig {

}
