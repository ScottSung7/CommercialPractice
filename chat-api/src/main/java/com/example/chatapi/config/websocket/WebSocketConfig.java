//package com.example.chatapi.config.websocket;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.socket.config.annotation.EnableWebSocket;
//import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
//import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
//
//@Configuration
//@EnableWebSocket
//public class WebSocketConfig implements WebSocketConfigurer {
//
//  @Override
//  public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
//    // WebSocket 핸들러를 등록합니다.
//    registry.addHandler(new MyWebSocketHandler(), "/ws/my-websocket").setAllowedOrigins("http://localhost:8080", "http://localhost:8081").withSockJS();
//  }
//}
