package com.example.accountapi.web.controller.account;


import com.example.accountapi.config.SpringSecurity.type.jwt.JWTUtil;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ATesterController {

    @Autowired
    private JWTUtil jwtUtil;

    @PostMapping("test/create/customer")
    @Operation(summary ="구매자 test@test.com 유저를 등록하고 테스트에 필요한 JWT 토큰을 생성합니다.")
    public String jtokenCustomer(){
        return jwtUtil.createJwt("tester@test.com", "CUSTOMER", 60*60*10*1000L);
    }
    @PostMapping("test/login/customer")
    @Operation(summary ="구매자 등록 후 이미 등록 후 토큰을 다시 받기 위해 요청합니다.")
    public String jtokenLoginCustomer(){
        return jwtUtil.createJwt("tester@test.com", "CUSTOMER", 60*60*10*1000L);
    }
    @PostMapping("test/create/seller")
    @Operation(summary ="판매자 test@test.com 유저를 등록하고 테스트에 필요한 JWT 토큰을 생성합니다.")
    public String jtokenSeller(){
        return jwtUtil.createJwt("tester@test.com", "SELLER", 60*60*10*1000L);
    }
    @PostMapping("test/login/seller")
    @Operation(summary ="판매자 등록 후 토큰을 다시 받기 위해 요청합니다.")
    public String jtokenLoginSeller(){
        return jwtUtil.createJwt("tester@test.com", "SELLER", 60*60*10*1000L);
    }

}
