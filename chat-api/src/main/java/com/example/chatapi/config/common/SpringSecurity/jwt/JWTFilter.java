package com.example.chatapi.config.common.SpringSecurity.jwt;

import com.example.chatapi.config.common.SpringSecurity.id.User;
import com.example.chatapi.config.common.SpringSecurity.id.UserPrincipalDetails;
import com.example.chatapi.config.common.validation.securityException.BasicException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static com.example.chatapi.config.common.validation.securityException.BasicErrorCode.TOKEN_EXPIRED;


@RequiredArgsConstructor
@Slf4j
public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authorization = request.getHeader("Authorization");

        //토큰이 없는 경우
        if(authorization == null || !authorization.startsWith("Bearer ")){
            filterChain.doFilter(request, response);
            return;
        }

        String token = authorization.split(" ")[1];
        String email = jwtUtil.getEmail(token);
        String type = jwtUtil.getType(token);
        Long id = jwtUtil.getId(token);

        //만료된 토큰
        if(jwtUtil.isExpired(token)){
            log.error(email + "의 토큰이 만료되었습니다.");
            throw new BasicException(TOKEN_EXPIRED);
            //filterChain.doFilter(request, response);
        }

        //Authentication
        User user = User.builder().email(email).type(type).id(id).build();
        Authentication authToken = new UsernamePasswordAuthenticationToken(new UserPrincipalDetails(user), null, new UserPrincipalDetails(user).getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authToken);
        filterChain.doFilter(request, response);
    }
}
