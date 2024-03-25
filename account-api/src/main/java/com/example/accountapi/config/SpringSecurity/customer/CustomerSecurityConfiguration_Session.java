package com.example.accountapi.config.SpringSecurity.customer;


import com.example.accountapi.config.SpringSecurity.AuthenticationFilter;
import com.example.accountapi.config.SpringSecurity.CorsConfig;
import jakarta.servlet.Filter;
import lombok.RequiredArgsConstructor;
import org.bouncycastle.asn1.x509.UserNotice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
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

        System.out.println("Customer Chain Checking");

        AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);

        http
                .authenticationProvider(customerAuthenticationProvider())
                .sessionManagement((sessionManagement) ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .addFilter(new AuthenticationFilter(authenticationManager))
                .securityMatcher("/accounts/customer/**")
                .formLogin(form -> form.disable())
//                .formLogin(form -> form.loginPage("/accounts/customer/login")
//                        .loginProcessingUrl("/accounts/customer/login")
//                        .successForwardUrl("/main"))
                 .httpBasic(httpBasic -> httpBasic.disable())
                 .addFilter(corsConfig.corsFilter())
                 .csrf(csrf->csrf.disable())
                .authorizeRequests((authorizeRequests) ->
                        authorizeRequests.anyRequest().permitAll()
                 );
//                .exceptionHandling((exceptionHandling) ->
//                        exceptionHandling.authenticationEntryPoint(new CustomAuthenticationEntryPoint())
//                );

        return http.build();
    }






}
