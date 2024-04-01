//package com.example.accountapi.config.SpringSecurity.type.session;
//
//
//import com.example.accountapi.config.SpringSecurity.CorsConfig;
//import com.example.accountapi.config.SpringSecurity.seller.SellerLogInService_SpringSecurity;
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.annotation.Order;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//@RequiredArgsConstructor
//@EnableWebSecurity
//@Order(1)
//public class SellerSecurityConfiguration_Session {
//
//    private final CorsConfig corsConfig;
//    @Bean
//    public PasswordEncoder sellerPasswordEncoder(){
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    public UserDetailsService sellerDetailService(){
//
//        return new SellerLogInService_SpringSecurity();
//    }
//    @Bean
//    public DaoAuthenticationProvider sellerAuthenticationProvider(){
//        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
//        provider.setUserDetailsService(sellerDetailService());
//        provider.setPasswordEncoder(sellerPasswordEncoder());
//        return provider;
//    }
//
//    @Bean
//    public SecurityFilterChain sellerChain(HttpSecurity http) throws Exception {
//        return http
//                .authenticationProvider(sellerAuthenticationProvider())
//                .securityMatcher("/accounts/seller/**")
//                .formLogin(form -> form.loginPage("/accounts/seller/login")
//                        .loginProcessingUrl("/accounts/seller/login")
//                        .successForwardUrl("/main"))
//                .authorizeHttpRequests((authz) -> authz
//                        .requestMatchers("/test").authenticated()
//                        .requestMatchers("/accounts/seller/login", "/accounts/seller/logout",
//                                "/accounts/seller/signup", "/main", "/").permitAll()
//                        .requestMatchers("/accounts/seller/**").authenticated()
//                )
//                .httpBasic(Customizer.withDefaults())
//                .addFilter(corsConfig.corsFilter())
//                .csrf(csrf->csrf.disable())
//                .build();
//    }
//
//
//
//
//
//}
