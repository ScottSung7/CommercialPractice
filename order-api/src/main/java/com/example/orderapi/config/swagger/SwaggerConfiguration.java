package com.example.orderapi.config.swagger;


import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class SwaggerConfiguration {
//    @Bean
//    public GroupedOpenApi group1(){
//        String[] paths = {"/**"};
//        return GroupedOpenApi.builder()
//                .group("client")
//                .pathsToMatch(paths)
//                .build();
//    }

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
                        .title("ORDER API")
                        .description("testing")
                        .version("v0.1"))
                        .components(new Components().addSecuritySchemes("bearerAuth", securityScheme))
                        .security(Arrays.asList(securityRequirement));


        return openAPI;
    }




}
