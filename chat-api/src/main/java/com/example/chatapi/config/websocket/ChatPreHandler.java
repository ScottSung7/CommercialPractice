package com.example.chatapi.config.websocket;

import com.example.chatapi.config.common.SpringSecurity.id.User;
import com.example.chatapi.config.common.SpringSecurity.jwt.JWTUtil;
import com.example.chatapi.config.common.validation.securityException.BasicException;

import com.example.chatapi.web.validations.exception.ChatException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

import static com.example.chatapi.config.common.validation.securityException.BasicErrorCode.*;
import static com.example.chatapi.web.validations.exception.ChatErrorCode.TOKEN_PARSING;

@RequiredArgsConstructor
@Component
@Slf4j
public class ChatPreHandler implements ChannelInterceptor {

    //private final JWTUtil jwtUtil;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
//        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(message);
//
//        String destination = headerAccessor.getDestination();
//
//        //CONNECT인경우는 제외
//        if(destination != null && destination.startsWith("/app")) {// 계속 helth check를 해서 걸린다. (connect & subscribe)
//
//
//            String authorization = headerAccessor.getFirstNativeHeader("authorization");
//            if(authorization == null || !authorization.startsWith("Bearer ")){
//                throw new BasicException(NO_TOKEN);
//            }
//            String token = authorization.split(" ")[1];
//            String email = jwtUtil.getEmail(token);
//            String type = jwtUtil.getType(token);
//            Long id = jwtUtil.getId(token);
//
//            //만료된 토큰
//            if (jwtUtil.isExpired(token)) {
//                log.error(email + "의 토큰이 만료되었습니다.");
//                throw new BasicException(TOKEN_EXPIRED);
//            }
//
//            //Authentication
//            User user = User.builder().email(email).type(type).id(id).build();
//
//            //Issue 1: @MessageMapping에서 받을 수 가 없음.
////            Authentication authToken = new UsernamePasswordAuthenticationToken(new UserPrincipalDetails(user), null, new UserPrincipalDetails(user).getAuthorities());
////            UserPrincipalDetails userPrincipalDetails = (UserPrincipalDetails) authToken.getPrincipal();
////            SecurityContextHolder.getContext().setAuthentication(authToken);
//
//        }

        return message;


    }
}
