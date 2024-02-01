package com.example.login.config;

import com.example.login.config.Jwt.JwtAuthenticationFilter;
import com.example.login.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final CorsConfig corsConfig;

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf->csrf.disable())
                .sessionManagement(policy -> policy.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .formLogin(form -> form.disable())
                .httpBasic(httpBasic -> httpBasic.disable())
                .with(new MyCustomDsl(), dsl -> {})

                .authorizeRequests()
                .requestMatchers("/api/v1/user/**")
                .access("hasRole('USER') or hasRole('MANAGER') or hasRole('ADMIN')")
                .requestMatchers("/api/v1/manager/**")
                .access("hasRole('MANAGER') or hasRole('ADMIN')")
                .requestMatchers("/api/v1/admin/**")
                .access("hasRole('ADMIN')")
                .anyRequest().permitAll()
                .and().build();
    }

    public class MyCustomDsl extends AbstractHttpConfigurer<MyCustomDsl, HttpSecurity> {


        @Override
        public void configure(HttpSecurity http) throws Exception {
            AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);

            http
                    .addFilter(corsConfig.corsFilter())
                    .addFilterBefore(new JwtAuthenticationFilter(authenticationManager), UsernamePasswordAuthenticationFilter.class);
            //.addFilter(new JwtAuthorizationFilter(authenticationManager, userRepository));
        }
    }




}
