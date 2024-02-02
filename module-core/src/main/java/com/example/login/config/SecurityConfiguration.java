package com.example.login.config;

import com.example.login.config.Jwt.JwtAuthenticationFilter;
import com.example.login.config.Jwt.JwtAuthorizationFilter;
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

import static org.springframework.security.authorization.AuthorityReactiveAuthorizationManager.hasAuthority;
import static org.springframework.security.authorization.AuthorityReactiveAuthorizationManager.hasRole;
import static org.springframework.security.authorization.AuthorizationManagers.allOf;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final CorsConfig corsConfig;

    private final UserRepository userRepository;


    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf->csrf.disable())
                .sessionManagement(policy -> policy.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .formLogin(form -> form.disable())
                .httpBasic(httpBasic -> httpBasic.disable())
                .with(new MyCustomDsl(), dsl -> {})

                .authorizeHttpRequests((authz) -> authz
                        .requestMatchers(SecurityProperties.UserLogin).hasRole("USER")
                        .requestMatchers(SecurityProperties.AdminPath).hasRole("ADMIN")
                        .anyRequest().permitAll())
              .build();
    }

    public class MyCustomDsl extends AbstractHttpConfigurer<MyCustomDsl, HttpSecurity> {


        @Override
        public void configure(HttpSecurity http) throws Exception {
            AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);

            http
                    .addFilter(corsConfig.corsFilter())
                    .addFilter(new JwtAuthenticationFilter(authenticationManager))
                    .addFilter(new JwtAuthorizationFilter(authenticationManager, userRepository));
        }
    }




}
