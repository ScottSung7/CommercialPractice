package com.example.login.config.Jwt;

import com.example.login.config.auth.PrincipalDetails;
import com.example.login.domain.Users;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

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

      } catch (IOException e) {
            e.printStackTrace();
            System.out.println("dddd");
        }
        System.out.println("outside");
        System.out.println(users);
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(users.getUsername(), users.getPassword());
        System.out.println("Processing?11");
        System.out.println(authenticationToken);
        //PrincipalDetailsService의 loadUserByUsername() 함수 실행 됨.
        System.out.println(authenticationManager);
        Authentication authentication =
                authenticationManager.authenticate(authenticationToken);
        System.out.println(authentication);
        System.out.println("Processing?22");
        //authentication 객체가 session 영역에 저장됨 => 로그인 됨.
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        System.out.println(principalDetails.getUser().getUsername());


        return authentication;

    }

}
