package com.example.chatapi.config.swagger;


import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class SwaggerConfiguration {


    @Bean
    public OpenAPI springOpenApi(){
        SecurityScheme securityScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .in(SecurityScheme.In.HEADER)
                .name("Authorization");
        SecurityRequirement securityRequirement = new SecurityRequirement().addList("bearerAuth");

        OpenAPI openAPI = new OpenAPI().info(new Info()
                        .title("Chat API")
                        .description("Chat API Test")
                        .version("v0.1"))
                        .components(new Components().addSecuritySchemes("bearerAuth", securityScheme))
                        .security(Arrays.asList(securityRequirement));


        return openAPI;
    }


}
