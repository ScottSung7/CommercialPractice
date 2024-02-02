package com.example.login.config.Jwt;

//권한이나 인증이 필요한 특정 주소를 요청했을떄 BasicAUthenticationFilter를 항상 타게 됨
//만약에 권한이 인증이 필요한 주소가 아니라면 이 필터를 안탐.

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.login.config.auth.PrincipalDetails;
import com.example.login.domain.Users;
import com.example.login.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private UserRepository userRepository;
    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UserRepository userRepository) {
        super(authenticationManager);
        this.userRepository = userRepository;
    }


    //인증이나 권한이 필요한 주소요청이 있을때 이 필터 타게 됨.
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("인증이나 권한이 필요한 주소 요청 됨.");

        String jwtHeader = request.getHeader(JwtProperties.HEADER_STRING);

        if (jwtHeader == null || !jwtHeader.startsWith(JwtProperties.TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }
        String jwtToken = request.getHeader(JwtProperties.HEADER_STRING).replace(JwtProperties.TOKEN_PREFIX, "");

        String username =
                JWT.require(Algorithm.HMAC512(JwtProperties.SECRET)).build().verify(jwtToken).getClaim("username").asString();

        if(username != null){
            Users userEntity = userRepository.findByUsername(username);
            PrincipalDetails principalDetails = new PrincipalDetails(userEntity);

            Authentication authentication =
                    new UsernamePasswordAuthenticationToken(principalDetails, null, principalDetails.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authentication);

        }

        chain.doFilter(request,response);




    }
}
