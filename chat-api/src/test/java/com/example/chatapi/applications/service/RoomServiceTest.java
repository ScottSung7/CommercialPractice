package com.example.chatapi.applications.service;

import com.example.chatapi.model.room.Room;
import com.example.chatapi.model.room.Users;
import com.example.chatapi.repository.UsersRepository;
import com.example.chatapi.web.RoomSaveForm;
import com.example.chatapi.web.UsersSaveForm;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RoomServiceTest {

    @Autowired
    private RoomService roomService;

    @Autowired
    private UsersRepository usersRepository;

    @Test
    void createRoom() {

        UsersSaveForm usersSaveForm1 = UsersSaveForm.builder().name("test1").email("tester1@naver.com").type("CUSTOMER").build();
        UsersSaveForm usersSaveForm2 = UsersSaveForm.builder().name("test2").email("tester@na.com").type("CUSTOMER").build();
        List<UsersSaveForm> users = List.of(usersSaveForm1, usersSaveForm2);

        RoomSaveForm form = RoomSaveForm.builder().name("test").users(users).build();

        Room room = roomService.createRoom(form);

        System.out.println(room.getUsers().size());
        System.out.println(room.getUsers().get(0).getName());
        System.out.println(room.getName());


    }
}
