package com.example.login.config.Jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.login.config.auth.PrincipalDetails;
import com.example.login.domain.Users;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Date;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {


    private final AuthenticationManager authenticationManager;


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        System.out.println("JwtAuthenciationFilter: 로그인 시도");

        Users users = new Users();
        try {
//            BufferedReader br = request.getReader();
//
//            String input = null;
//            while ((input = br.readLine()) != null) {
//                System.out.println(input);
//            }

            ObjectMapper om = new ObjectMapper();
            users = om.readValue(request.getInputStream(), Users.class);

            System.out.println(users);
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(users.getUsername(), users.getPassword());
            //PrincipalDetailsService의 loadUserByUsername() 함수 실행 됨. 그 후 정상이면 authentication 리턴 됨.
            Authentication authentication =
                    authenticationManager.authenticate(authenticationToken);

            return authentication;
      } catch (IOException e) {
            e.printStackTrace();
        }


        return null;

    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        System.out.println("successfulAuthentication 실행됨: 인증 완료");
        PrincipalDetails principalDetails = (PrincipalDetails) authResult.getPrincipal();
        System.out.println("로그인 완료됨: "+principalDetails.getUser().getUsername());
        //authentication 값이 있다는 것은 로그인 되었다는 뜻.
        //authentication 객체가 session 영역에 저장됨 => 로그인 됨.

        String jwtToken = JWT.create()
                .withSubject("cos토큰")
                .withExpiresAt(new Date(System.currentTimeMillis() + JwtProperties.EXPIRATION_TIME))
                .withClaim("id", principalDetails.getUser().getId())
                .withClaim("username", principalDetails.getUser().getUsername())
                .sign(Algorithm.HMAC512(JwtProperties.SECRET));

        response.addHeader("Authorization", "Bearer "+jwtToken);


    //    super.successfulAuthentication(request, response, chain, authResult);

    }
}
