package com.example.chatapi.web.controller.user;

import com.example.chatapi.applications.service.UsersService;
import com.example.chatapi.config.common.SpringSecurity.id.UserPrincipalDetails;
import com.example.chatapi.config.common.validation.LoginCheckMSA;
import com.example.chatapi.model.dto.user.RoomListDTO;
import com.example.chatapi.model.dto.user.UserDTO;
import com.example.chatapi.model.entity.roomAndUser.Users;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/chat-info")
@RequiredArgsConstructor
public class UsersInfoController {

    private final UsersService usersService;

    //Chat-Info
    @PostMapping("/myInfo")
    public ResponseEntity<?> getMyInfo(Authentication authentication){
        UserPrincipalDetails userPrincipal = LoginCheckMSA.userCheck(authentication);
        Users user = usersService.findUsersByEmailAndType(userPrincipal.getEmail(), userPrincipal.getType());
        return ResponseEntity.ok(UserDTO.from(user));
    }

    @PostMapping("/userInfo")
    @Parameter
            (name = "email", example = "tester@test.com")
    @Parameter(name = "type",  example = "CUSTOMER")
    public ResponseEntity<?> getUserBasicInfo(@RequestParam("email") String email, @RequestParam("type") String type){

        Users user = usersService.findUsersByEmailAndType(email, type);
        return ResponseEntity.ok(UserDTO.from(user));
    }

    @PostMapping("/roomList")
    @Parameter
            (name = "email", example = "tester@test.com")
    @Parameter(name = "type",  example = "CUSTOMER")
    public ResponseEntity<List<RoomListDTO>> getInvolvedRoomNameList(@RequestParam("email") String email, @RequestParam("type") String type){

        Users user = usersService.findUsersByEmailAndType(email, type);
        return ResponseEntity.ok(user.getInvolvedRooms().stream()
                        .map(involved -> RoomListDTO.from(involved.getRoom())).collect(Collectors.toList()));
    }





}
