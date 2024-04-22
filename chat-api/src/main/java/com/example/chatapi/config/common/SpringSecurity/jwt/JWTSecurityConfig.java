package com.example.chatapi.config.common.SpringSecurity.jwt;



import com.example.chatapi.config.common.SpringSecurity.CorsConfig_SpringSecurity;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class JWTSecurityConfig {

    private final JWTUtil jwtUtil;
    private final CorsConfig_SpringSecurity corsConfigSpringSecurity;



    @Bean
    public SecurityFilterChain jwtChain(HttpSecurity http) throws Exception {

        http
                .formLogin(form -> form.disable())
                .httpBasic(httpBasic -> httpBasic.disable())
                .csrf(csrf->csrf.disable())
                .sessionManagement((sessionManagement) ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .exceptionHandling((exceptionHandling) ->
                        exceptionHandling.authenticationEntryPoint(new CustomAuthenticationEntryPoint()))
                .addFilterBefore(new JWTFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class)
                .addFilter(corsConfigSpringSecurity.corsFilter())
                .authorizeRequests((authorizeRequests) -> authorizeRequests
                        //swagger
                        .requestMatchers("/index.html/" , "/swagger-ui/**", "/health",
                                "/swagger-resources/**", "/api-docs/**"  ).permitAll()
                        .requestMatchers("/", "chat2", "/app.js", "/ws/**", "/gs-guide-websocket/**").permitAll()
                        .anyRequest().permitAll()
                        //.anyRequest().authenticated()
                );

        return http.build();
    }







}
