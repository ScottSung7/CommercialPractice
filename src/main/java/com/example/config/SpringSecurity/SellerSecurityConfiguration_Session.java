package com.example.config.SpringSecurity;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Component;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@Order(1)
public class SellerSecurityConfiguration_Session {

    private final CorsConfig corsConfig;
    @Bean
    public PasswordEncoder sellerPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService sellerDetailService(){

        return new SellerLogInService_SpringSecurity();
    }
    @Bean
    public DaoAuthenticationProvider sellerAuthenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(sellerDetailService());
        provider.setPasswordEncoder(sellerPasswordEncoder());
        return provider;
    }

    @Bean
    public SecurityFilterChain sellerChain(HttpSecurity http) throws Exception {
        return http
                .authenticationProvider(sellerAuthenticationProvider())
                .formLogin(form -> form.loginPage("/seller/login")
                        .loginProcessingUrl("/sellerLogin")
                        .successForwardUrl("/main"))
                .authorizeHttpRequests((authz) -> authz
                        .requestMatchers("/seller").authenticated()
                        .requestMatchers("/test").authenticated()
                        .requestMatchers("accounts/**", "/main").permitAll()
                        .anyRequest().permitAll()
                )
                //.cors(cors -> cors.disable())
                .addFilter(corsConfig.corsFilter())
                .csrf(csrf->csrf.disable())
              .build();
    }





}
