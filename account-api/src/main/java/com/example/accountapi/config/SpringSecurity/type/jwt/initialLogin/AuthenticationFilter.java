package com.example.accountapi.config.SpringSecurity.type.jwt.initialLogin;

import com.example.accountapi.config.SpringSecurity.id.customer.CustomerPrincipalDetails;
import com.example.accountapi.config.SpringSecurity.id.seller.SellerPrincipalDetails;
import com.example.accountapi.config.SpringSecurity.type.jwt.JWTUtil;
import com.example.accountapi.web.validation.exception.AccountException;
import com.example.accountapi.web.validation.form.LogInForm;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;

import static com.example.accountapi.web.validation.exception.ErrorCode.LOGIN_FAILED;
import static com.example.accountapi.web.validation.exception.ErrorCode.LOGIN_TYPE_NOT_EXIST;

@RequiredArgsConstructor
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final DaoAuthenticationProvider customerAuthenticationProvider;
    private final DaoAuthenticationProvider sellerAuthenticationProvider;
    private final JWTUtil jwtUtil;

    private final ObjectMapper mapper = new ObjectMapper();
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {

        try {
            //파싱
            LogInForm creds;

            if(request.getContentType().equals("application/json")){
                //raw data 파싱
                creds = mapper.readValue(request.getInputStream(), LogInForm.class);
            }else {
                //form data 파싱
                Map<String, String> formData = request.getParameterMap().entrySet().stream()
                        .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue()[0]));
                String formDataString = mapper.writeValueAsString(formData);
                creds = mapper.readValue(formDataString, LogInForm.class);
            }

            //로그인
            UsernamePasswordAuthenticationToken token =
                    new UsernamePasswordAuthenticationToken(creds.getEmail(), creds.getPassword(), new ArrayList<>());
            if(creds.getType().equals("CUSTOMER")){
                return customerAuthenticationProvider.authenticate(token);
            }else if (creds.getType().equals("SELLER")){
                return sellerAuthenticationProvider.authenticate(token);
            }else{
                throw new AccountException(LOGIN_TYPE_NOT_EXIST);
            }

        }catch(IOException e){
            throw new AccountException(LOGIN_FAILED);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        //TODO:이메일 인증과 토큰 엮기.
        String type = request.getParameter("type");
        String email;
        Long id;
        if(type.equals("CUSTOMER")) {
            CustomerPrincipalDetails customUserDetails = (CustomerPrincipalDetails) authentication.getPrincipal();
            email = customUserDetails.getEmail();
            id = customUserDetails.getId();
        }else if(type.equals("SELLER")) {
            SellerPrincipalDetails sellerUserDetails = (SellerPrincipalDetails) authentication.getPrincipal();
            email = sellerUserDetails.getEmail();
            id = sellerUserDetails.getId();
        }else{
            throw new AccountException(LOGIN_TYPE_NOT_EXIST);
        }

//        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
//        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
//        GrantedAuthority auth = iterator.next();
//
//        String role = auth.getAuthority();

        String token = jwtUtil.createJwt(email, type, id, 60*60*10*1000L);

        response.addHeader("Authorization", "Bearer " + token);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        super.unsuccessfulAuthentication(request, response, failed);
    }
}
