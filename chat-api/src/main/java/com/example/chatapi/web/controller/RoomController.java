package com.example.chatapi.web.controller;



import com.example.chatapi.applications.service.RoomService;
import com.example.chatapi.applications.tools.client.AccountFeignClient;
import com.example.chatapi.model.room.Users;
import com.example.chatapi.repository.UsersRepository;
import com.example.chatapi.web.RoomSaveForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/chat")
public class RoomController {



    @PostMapping("/leave")
    public ResponseEntity<?> leaveRoom(){
        return null;
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteRoom(){
        return null;
    }


    @Autowired
    private AccountFeignClient accountFeignClient;

    @PostMapping("/testing")
    public ResponseEntity<?> test(){
        String test = accountFeignClient.accountTest().getBody();
        return ResponseEntity.ok(test);
    }

    @PostMapping("/testing2")
    public ResponseEntity<?> test2(){

        return ResponseEntity.ok("test");
    }


    @Autowired
    private UsersRepository usersRepository;

    @PostMapping("/testint3")
    public void save(){
        Users user1 = Users.builder().email("test1").build();
        Users user2 = Users.builder().email("test2").build();
        usersRepository.save(user1);
        usersRepository.save(user2);
    }

    @Autowired
    private RoomService roomService;
    @PostMapping("/invite")
    public ResponseEntity<?> createRoom(@RequestBody RoomSaveForm form){
        roomService.createRoom(form);

        return null;
    }






}
