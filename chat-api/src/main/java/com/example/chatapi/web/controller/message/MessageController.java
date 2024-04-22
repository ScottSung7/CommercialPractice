package com.example.chatapi.web.controller.message;


import com.example.chatapi.applications.application.RoomApplication;
import com.example.chatapi.applications.service.MessageService;
import com.example.chatapi.applications.service.UsersService;
import com.example.chatapi.config.common.SpringSecurity.id.User;
import com.example.chatapi.config.common.SpringSecurity.jwt.JWTUtil;
import com.example.chatapi.config.common.validation.securityException.BasicException;
import com.example.chatapi.model.basics.Greeting;
import com.example.chatapi.model.dto.message.MessageDTO;
import com.example.chatapi.model.entity.roomAndUser.Room;
import com.example.chatapi.model.entity.roomAndUser.Users;
import com.example.chatapi.model.entity.message.Message;
import com.example.chatapi.web.validations.form.MessageForm;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

import static com.example.chatapi.config.common.validation.securityException.BasicErrorCode.NO_TOKEN;

@Controller
@RequiredArgsConstructor
public class MessageController {


  private final JWTUtil jwtUtil;

  private final RoomApplication roomApplication;
  private final MessageService messageService;
  private final UsersService usersService;
  @MessageMapping("/hello/{id}")
  @SendTo("/topic/greetings")
  public MessageDTO greeting2(@DestinationVariable String id, @Header("authorization") String token, MessageForm inMessage) throws Exception {

    Thread.sleep(1000); // simulated delay
    User LoginInfo = checkToken(token);

    //TODO:LoginInfo에 userEmail이 있지만 닉네임을 얻어야 하고, 방을 지정해야 되서 가져와야 한다. 속도 문제로 캐싱이나 html에 hidden으로 숨겨 놓았다가 가져오는 방법도 고려.
    Users user = usersService.findUsersByEmailAndType(LoginInfo.getEmail(), LoginInfo.getType());
    Room room = roomApplication.findRoomById(id);

    Message message = Message.of(room, user, inMessage.getContent());
    message = messageService.saveMessage(message);

    return MessageDTO.from(message);

  }

  private User checkToken(String authorization){
    //TODO:필터에서 Authentication객체에 담기지도 Header에 담기지도 않아서 여기서 인증. -> 가능한 방법 찾아보기.
    if(authorization == null || !authorization.startsWith("Bearer ")){
      throw new BasicException(NO_TOKEN);
    }
    String token = authorization.split(" ")[1];
    String email = jwtUtil.getEmail(token);
    String type = jwtUtil.getType(token);
    Long id = jwtUtil.getId(token);

    return User.builder().email(email).type(type).id(id).build();
  }


}
