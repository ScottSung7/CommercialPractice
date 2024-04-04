package com.example.accountapi.config.SpringSecurity.type.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest req, HttpServletResponse res, AuthenticationException authException) throws IOException, ServletException {
        res.setContentType("application/json;charset=UTF-8");
        res.setStatus(403);
        Map<String, Object> body = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        body.put("status", 403);
        body.put("message", "로그인이 필요한 요청입니다.");

        res.getWriter().write(mapper.writeValueAsString(body));
    }

}