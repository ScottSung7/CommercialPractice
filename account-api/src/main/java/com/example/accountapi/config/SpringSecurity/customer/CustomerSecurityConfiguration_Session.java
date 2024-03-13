package com.example.accountapi.config.SpringSecurity.customer;


import com.example.accountapi.config.SpringSecurity.AuthenticationFilter;
import com.example.accountapi.config.SpringSecurity.CorsConfig;
import jakarta.servlet.Filter;
import lombok.RequiredArgsConstructor;
import org.bouncycastle.asn1.x509.UserNotice;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

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
        AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);

        http
                .authenticationProvider(customerAuthenticationProvider())
                .addFilter(new AuthenticationFilter(authenticationManager))
                .securityMatcher("/accounts/customer/**")
                .formLogin(form -> form.loginPage("/accounts/customer/login")
                        .loginProcessingUrl("/accounts/customer/login")
                        .successForwardUrl("/main"))
                 .httpBasic(Customizer.withDefaults())
                 .addFilter(corsConfig.corsFilter())
                 .csrf(csrf->csrf.disable())
                 .authorizeHttpRequests((authz) -> authz
                        .requestMatchers("/test").authenticated()
                        .requestMatchers("/accounts/customer/login", "/accounts/customer/logout",
                                "/accounts/customer/signup",
                                "/", "/main").permitAll()
                        .requestMatchers("/accounts/customer/**").authenticated()
                );
         return http.build();
    }






}