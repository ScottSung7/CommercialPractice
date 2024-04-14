package com.example.accountapi.config.SpringSecurity.type.jwt;

import com.example.accountapi.config.SpringSecurity.id.customer.CustomerPrincipalDetails;
import com.example.accountapi.config.SpringSecurity.id.seller.SellerPrincipalDetails;
import com.example.accountapi.domain.model.Customer;
import com.example.accountapi.domain.model.Seller;
import com.example.accountapi.web.validation.exception.AccountException;
import com.example.accountapi.web.validation.exception.ErrorCode;
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

@RequiredArgsConstructor
@Slf4j
public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authorization = request.getHeader("Authorization");
        System.out.println("필터");
        //토큰이 없는 경우
        if(authorization == null || !authorization.startsWith("Bearer ")){
            System.out.println("토큰 없음");
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
            throw new AccountException(ErrorCode.TOKEN_EXPIRED);
            //filterChain.doFilter(request, response);
        }

        //Authentication
        Authentication authToken = null;
        if(type.equals("CUSTOMER")){

            Customer customer = Customer.builder().email(email).id(id).build();
            authToken = new UsernamePasswordAuthenticationToken(new CustomerPrincipalDetails(customer), null, new CustomerPrincipalDetails(customer).getAuthorities());
        }else if(type.equals("SELLER")){
            Seller seller = Seller.builder().email(email).id(id).build();
            authToken = new UsernamePasswordAuthenticationToken(new SellerPrincipalDetails(seller), null, new SellerPrincipalDetails(seller).getAuthorities());
        }else{
            throw new AccountException(ErrorCode.LOGIN_TYPE_NOT_EXIST);
        }

        SecurityContextHolder.getContext().setAuthentication(authToken);
        filterChain.doFilter(request, response);
    }
}
