//package com.example.chatapi.config.websocket;
//
//import com.example.chatapi.model.basics.HelloMessage;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.web.socket.CloseStatus;
//import org.springframework.web.socket.TextMessage;
//import org.springframework.web.socket.WebSocketHandler;
//import org.springframework.web.socket.WebSocketMessage;
//import org.springframework.web.socket.WebSocketSession;
//
//public class MyWebSocketHandler implements WebSocketHandler {
//
//    @Override
//    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
//        System.out.println("소켓 연결됨.");
//
//    }
//
//    @Override
//    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
//        // 클라이언트로부터 메시지를 받으면 호출됩니다.
//        String payload = (String) message.getPayload();
//        System.out.println("payload = " + payload);
//        ObjectMapper mapper = new ObjectMapper();
//        HelloMessage hellomessage = mapper.readValue(payload, HelloMessage.class);
//
//
//        session.sendMessage(new TextMessage("Hello" + hellomessage.getName() + "!"));
//        // 받은 메시지를 처리합니다.
//
//    }
//
//    @Override
//    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
//        // 전송 오류가 발생하면 호출됩니다.
//    }
//
//    @Override
//    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
//        // 클라이언트와의 연결이 종료되면 호출됩니다.
//    }
//
//    @Override
//    public boolean supportsPartialMessages() {
//
//        return false;
//    }
//}
