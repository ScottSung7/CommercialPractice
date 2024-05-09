package com.example.orderapi.config.common.SpringSecurity.jwt;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JWTUtilTest {

    @Autowired
    private JWTUtil jwtUtil;
    String email;
    String type;
    Long expiredMs;
    Long id;
    @BeforeEach
    private void before(){
        email = "test@example.com";
        type = "testType";
        expiredMs = 3600000L;
        id = 1L;
    }

    @Test
    void getEmail() {
        String token = jwtUtil.createJwt(email, type, id, expiredMs);
        assertEquals(email, jwtUtil.getEmail(token));
    }

    @Test
    void getType() {
        String token = jwtUtil.createJwt(email, type, id, expiredMs);
        assertEquals(type, jwtUtil.getType(token));
    }

    @Test
    void getId() {
        String token = jwtUtil.createJwt(email, type, id, expiredMs);
        assertNotNull(jwtUtil.getId(token));
    }

    @Test
    void isExpired() {
        String token = jwtUtil.createJwt(email, type, id, expiredMs);
        assertFalse(jwtUtil.isExpired(token));
    }

    @Test
    void createJwt() {
        String token = jwtUtil.createJwt(email, type, id, expiredMs);
        assertNotNull(token);
        assertEquals(email, jwtUtil.getEmail(token));
        assertEquals(type, jwtUtil.getType(token));
    }
}