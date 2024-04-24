package com.example.chatapi.web.controller.user;

import com.example.chatapi.applications.tools.client.AccountFeignClient;
import com.example.chatapi.config.common.SpringSecurity.id.UserPrincipalDetails;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/chat-search")
@RequiredArgsConstructor
public class UsersSearchController {

    private final AccountFeignClient accountFeignClient;

    //초기 - authentication에서 type과 메시지를 받아서 확인을하고 보낸다. //초대를 받을 때 기본 정보는 추가 되니 그렇지 않은 경우. (초대 받은 적 없이 채팅 시작 시)
    public void startChat(Authentication authentication){
        UserPrincipalDetails user = (UserPrincipalDetails) authentication.getPrincipal();
        System.out.println(user.getEmail());
        System.out.println(user.getType());
        System.out.println(user.getId());

    }//스프링 시큐리티 때

    @PostMapping("/testing")//검색 (Customer/ Seller)
    public ResponseEntity<?> test(@Parameter(hidden=true) @RequestHeader("Authorization") String token){
        String test = accountFeignClient.accountTest(token).getBody();
        return ResponseEntity.ok(test);
    }
    //Search
    @PostMapping("/customer")
    public ResponseEntity<?> getCustomerInfoForChat(
            @Parameter(hidden = true) @RequestHeader("Authorization") String token,
            @Parameter(example="tester@test.com") @RequestParam("email") String email){
        return ResponseEntity.ok(accountFeignClient.getCustomerInfoForChat(email, token).getBody());
    }

    @PostMapping("/seller")
    public ResponseEntity getSellerInfoForChat(
            @Parameter(hidden = true) @RequestHeader("Authorization") String token,
            @Parameter(example="tester@test.com") @RequestParam("email") String email){
        return ResponseEntity.ok(accountFeignClient.getSellerInfoForChat(email, token).getBody());
    }

    @PostMapping("/user")
    public ResponseEntity<?> getUserInfoForChat(
            @Parameter(hidden = true) @RequestHeader("Authorization") String token,
            @Parameter(example="tester@test.com") @RequestParam("email") String email){
        return ResponseEntity.ok(accountFeignClient.getUserInfoForChat(email, token).getBody());
    }





}
