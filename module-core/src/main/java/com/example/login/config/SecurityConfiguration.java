package com.example.login.config;

import com.example.login.config.Jwt.JwtAccessDeniedHandler;
import com.example.login.config.Jwt.JwtAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {


    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {


        http
            .authorizeHttpRequests((authz) -> authz
                    .requestMatchers("/","/user/**", "/chat/test/**", "/chat/record/**").permitAll()
                    .requestMatchers("/admin").hasRole("ADMIN")
                    .anyRequest().authenticated())
                .csrf(csrf -> csrf.disable())
            .exceptionHandling((exception) -> exception
                    .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                    .accessDeniedHandler(jwtAccessDeniedHandler))
            .headers(headers -> headers
                    .frameOptions(frameOptions-> frameOptions
                        .sameOrigin()))
            .sessionManagement((session)->
                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

            .formLogin((formLogin)->{})
            .httpBasic(withDefaults());
        return http.build();
    }
    //.csrf((csrf)->csrf.ignoringRequestMatchers("/user/**", "/chat/test/**", "/chat/record/**"))


    @Bean
    public PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }


}
