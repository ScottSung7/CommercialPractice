package com.example.accountapi.config;



import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {
    @Bean
    public GroupedOpenApi group1(){
        String[] paths = {"/**"};
        return GroupedOpenApi.builder()
                .group("client")
                .pathsToMatch(paths)
                .build();
    }

    @Bean
    public OpenAPI springOpenApi(){
        return new OpenAPI().info(new Info()
                                .title("API")
                                .description("testing")
                                .version("v0.1"));
    }
}
