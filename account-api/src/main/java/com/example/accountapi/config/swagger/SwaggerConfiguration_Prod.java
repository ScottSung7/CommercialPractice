package com.example.accountapi.config.swagger;


import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Arrays;

@Configuration
@RequiredArgsConstructor
@Profile("prod")
public class SwaggerConfiguration_Prod {
//    @Bean
//    public GroupedOpenApi group1(){
//        String[] paths = {"/**"};
//        return GroupedOpenApi.builder()
//                .group("client")
//                .group("admin")
//                .pathsToMatch(paths)
//                .build();
//    }

    @Value("${cloud.account-api.url}")
    private String urlname;


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
                        .title("Account API")
                        .description("testing")
                        .version("v0.1"))
                        .addServersItem(new Server().url(urlname))
                        .components(new Components().addSecuritySchemes("bearerAuth", securityScheme))
                        .security(Arrays.asList(securityRequirement));

        return openAPI;
    }


//    @Autowired
//     JWTUtil jwtUtil;
//    @Bean
//    public OperationCustomizer operationCustomizer() {
//
//        String key = jwtUtil.createJwt("test@test.com", "CUSTOMER",  60 * 60 * 10L);
//
//        return (operation, handlerMethod) -> {
//            operation.addParametersItem(
//                    new Parameter()
//                            .in(ParameterIn.HEADER.toString())
//                            .required(false)
//                            .schema(new StringSchema().example(key))
//                            .description("api를 사용하기 위한 인증키")
//                            .name("인증 키")
//
//            );
//
//            return operation;
//        };
//    }


}
