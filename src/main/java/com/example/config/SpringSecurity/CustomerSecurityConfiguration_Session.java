package com.example.config.SpringSecurity;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
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
            //    .sessionManagement(policy -> policy.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .formLogin(form -> form.loginPage("/customer/login")
                        .loginProcessingUrl("/customerLogin")
                        .successForwardUrl("/main"))
                .authorizeHttpRequests((authz) -> authz
                        .requestMatchers("/customer").authenticated()
                        .requestMatchers("/test").authenticated()
                        .requestMatchers("accounts/**", "/main").permitAll()
                        .anyRequest().permitAll()
                )
                .httpBasic(Customizer.withDefaults())
                .addFilter(corsConfig.corsFilter())
              //  .addFilter(corsConfig.corsFilter())
                .csrf(csrf->csrf.disable())
              .build();
    }





}
