package com.example.chatapi.web.controller.room;



import com.example.chatapi.applications.application.RoomApplication;
import com.example.chatapi.applications.service.MessageService;
import com.example.chatapi.model.dto.message.MessageDTO;
import com.example.chatapi.model.entity.message.Message;
import com.example.chatapi.model.entity.roomAndUser.Room;
import com.example.chatapi.model.dto.room.RoomDTO;
import com.example.chatapi.web.validations.form.room.RoomSaveForm;
import com.example.chatapi.web.validations.form.room.RoomAddUserForm;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
public class RoomController {

    private final RoomApplication roomApplication;
    //TODO: UPDATE, DELETE


    @PostMapping("/invite") //유저 정보는 프론트에서 검색을 통해서 넘어올 예정. -> 이부분을 백에서 하는게 좋을지도. (이메일과 타입 정도만 받고)
    @Schema(name = "RoomSaveForm", implementation = RoomSaveForm.class)
    public ResponseEntity<RoomDTO> createRoom(@RequestBody RoomSaveForm form){
        Room room = roomApplication.createRoom(form);
        return ResponseEntity.ok(RoomDTO.from(room));
    }

    @PostMapping("/addParticipants")
    @Schema(name = "RoomAddUserForm", implementation = RoomAddUserForm.class)
    public ResponseEntity<RoomDTO> addParticipans(@RequestBody RoomAddUserForm form){
        Room room = roomApplication.addUsers(form);
        return ResponseEntity.ok(RoomDTO.from(room));
    }

    //info
    @GetMapping("/{id}/userlist")
    public ResponseEntity<RoomDTO> getUserList(@PathVariable("id") String id){
        Room room = roomApplication.findRoomById(id);
        return ResponseEntity.ok(RoomDTO.from(room));
    }
    private final MessageService messageService;
    @GetMapping("/{id}")
    public ResponseEntity<List<MessageDTO>> getAllChat(@PathVariable("id") String id){
        List<Message> messages = messageService.getAllMessage(id);
        return ResponseEntity.ok(messages.stream().map(MessageDTO::from).collect(Collectors.toList()));
    }






}
