package com.example.accountapi.config.swagger;



import com.example.accountapi.config.SpringSecurity.type.jwt.JWTUtil;
import io.jsonwebtoken.Jwts;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.servers.Servers;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.parameters.HeaderParameter;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

@Configuration
public class SwaggerConfiguration {
    @Bean
    public GroupedOpenApi group1(){
        String[] paths = {"/**"};
        return GroupedOpenApi.builder()
                .group("client")
                .pathsToMatch(paths)
       //         .addOperationCustomizer(operationCustomizer())
                .build();
    }

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
                        .title("API")
                        .description("testing")
                        .version("v0.1"))
                        .servers(Arrays.asList(
                                new Server().url("http://localhost:8080").url("http://localhost:8081")
                        ))
                        .components(new Components().addSecuritySchemes("bearerAuth", securityScheme))
                        .security(Arrays.asList(securityRequirement));


        return openAPI;
    }

    @Autowired
     JWTUtil jwtUtil;
    @Bean
    public OperationCustomizer operationCustomizer() {

        String key = jwtUtil.createJwt("test@test.com", "CUSTOMER",  60 * 60 * 10L);

        return (operation, handlerMethod) -> {
            operation.addParametersItem(
                    new Parameter()
                            .in(ParameterIn.HEADER.toString())
                            .required(false)
                            .schema(new StringSchema().example(key))
                            .description("api를 사용하기 위한 인증키")
                            .name("인증 키")

            );

            return operation;
        };
    }


}
