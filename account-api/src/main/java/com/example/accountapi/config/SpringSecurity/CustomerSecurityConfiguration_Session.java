package com.example.accountapi.config.SpringSecurity;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@Order(2)
public class CustomerSecurityConfiguration_Session {

    private final CorsConfig corsConfig;
    @Bean
    public PasswordEncoder customerPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService customerDetailService(){
        return new CustomerLogInService_SpringSecurity();
    }
    @Bean
    public DaoAuthenticationProvider customerAuthenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(customerDetailService());
        provider.setPasswordEncoder(customerPasswordEncoder());
        return provider;
    }
    @Bean
    public SecurityFilterChain customerChain(HttpSecurity http) throws Exception {
        return http
                .authenticationProvider(customerAuthenticationProvider())
                .securityMatcher("/accounts/customer/**")
                .formLogin(form -> form.loginPage("/accounts/customer/login")
                        .loginProcessingUrl("/accounts/customer/login")
                        .successForwardUrl("/main"))
                .authorizeHttpRequests((authz) -> authz
                        .requestMatchers("/test").authenticated()
                        .requestMatchers("/accounts/customer/login", "/accounts/customer/logout",
                                "/accounts/customer/signup",
                                "/", "/main").permitAll()
                        .requestMatchers("/accounts/customer/**").authenticated()
                )
                .httpBasic(Customizer.withDefaults())
                .addFilter(corsConfig.corsFilter())
                .csrf(csrf->csrf.disable())
                .build();
    }





}
