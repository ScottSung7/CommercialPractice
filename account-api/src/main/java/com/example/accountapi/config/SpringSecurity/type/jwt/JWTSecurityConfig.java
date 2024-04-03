package com.example.accountapi.config.SpringSecurity.type.jwt;


import com.example.accountapi.config.SpringSecurity.CorsConfig;
import com.example.accountapi.config.SpringSecurity.customer.CustomerLogInService_SpringSecurity;
import com.example.accountapi.config.SpringSecurity.seller.SellerLogInService_SpringSecurity;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class JWTSecurityConfig {

    private final JWTUtil jwtUtil;
    private final CorsConfig corsConfig;
    @Bean
    public PasswordEncoder customerPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public PasswordEncoder sellerPasswordEncoder(){ return new BCryptPasswordEncoder();
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
    public UserDetailsService sellerDetailService(){ return new SellerLogInService_SpringSecurity();}
    @Bean
    public DaoAuthenticationProvider sellerAuthenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(sellerDetailService());
        provider.setPasswordEncoder(sellerPasswordEncoder());
        return provider;
    }
//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception{
//        return configuration.getAuthenticationManager();
//    }

    @Bean
    public SecurityFilterChain jwtChain(HttpSecurity http) throws Exception {

        http
                .formLogin(form -> form.disable())
                .httpBasic(httpBasic -> httpBasic.disable())
                .csrf(csrf->csrf.disable())
                .sessionManagement((sessionManagement) ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .addFilter(corsConfig.corsFilter())
                .addFilterBefore(new JWTFilter(jwtUtil), AuthenticationFilter.class)
                .addFilterAt(new AuthenticationFilter(customerAuthenticationProvider(), sellerAuthenticationProvider(), jwtUtil), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests((authorizeRequests) -> authorizeRequests
                        //login & Home
                        .requestMatchers("/accounts/customer/login", "/").permitAll()
                        .requestMatchers("/accounts/seller/login").permitAll()
                        //signup
                        .requestMatchers("/accounts/customer/signup").permitAll()
                        .requestMatchers("/accounts/seller/signup").permitAll()
                        //verify
                        .requestMatchers("/accounts/customer/verify/**/**").permitAll()
                        .requestMatchers("/accounts/seller/verify/**/**").permitAll()
                        //swagger
                        .requestMatchers("/index.html/" , "/swagger-ui/**", "/health",
                                        "/swagger-resources/**", "/api-docs/**"  ).permitAll()
                        //a-tester-controller
                        .requestMatchers("/test/**").permitAll()
                        .anyRequest().authenticated()
                );

        return http.build();
    }







}
