package com.example.orderapi.config.common.SpringSecurity.jwt;

import com.example.orderapi.config.common.SpringSecurity.id.UserPrincipalDetails;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

class JWTFilterTest {
        private JWTUtil jwtUtil = mock(JWTUtil.class);
        private JWTFilter jwtFilter = new JWTFilter(jwtUtil);

        @Test
        public void testDoFilterInternal() throws Exception {
            HttpServletRequest request = mock(HttpServletRequest.class);
            HttpServletResponse response = mock(HttpServletResponse.class);
            FilterChain filterChain = mock(FilterChain.class);

            when(request.getHeader("Authorization")).thenReturn("Bearer token");
            when(jwtUtil.getEmail("token")).thenReturn("test@example.com");
            when(jwtUtil.getType("token")).thenReturn("type");
            when(jwtUtil.getId("token")).thenReturn(1L);
            when(jwtUtil.isExpired("token")).thenReturn(false);

            jwtFilter.doFilterInternal(request, response, filterChain);

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            assertNotNull(authentication);
            //assertEquals("test@example.com", ((UserPrincipalDetails) authentication.getPrincipal()).getUsername());
        }

}