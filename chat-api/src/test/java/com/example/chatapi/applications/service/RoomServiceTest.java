//package com.example.chatapi.applications.service;
//
//import com.example.chatapi.applications.application.RoomApplication;
//import com.example.chatapi.model.entity.Room;
//import com.example.chatapi.repository.UsersRepository;
//import com.example.chatapi.web.RoomSaveForm;
//import com.example.chatapi.web.UsersSaveForm;
//import org.junit.jupiter.api.MethodOrderer;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.TestMethodOrder;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//
//@SpringBootTest
//@TestMethodOrder(value = MethodOrderer.MethodName.class)
//@Transactional
//class RoomServiceTest {
//
//    @Autowired
//    private RoomService roomService;
//
//    @Autowired
//    private UsersRepository usersRepository;
//
//    @Autowired
//    RoomApplication roomApplication;
//
//    @Test
//    void createRoom() {
//
//        UsersSaveForm usersSaveForm1 = UsersSaveForm.builder().name("test1").email("tester1@naver.com").type("CUSTOMER").build();
//        UsersSaveForm usersSaveForm2 = UsersSaveForm.builder().name("test2").email("tester@na.com").type("CUSTOMER").build();
//        List<UsersSaveForm> users = List.of(usersSaveForm1, usersSaveForm2);
//
//        UsersSaveForm usersSaveForm3 = UsersSaveForm.builder().name("test3").email("sss@dddd.com").type("CUSTOMER").build();
//        List<UsersSaveForm> users2 = List.of(usersSaveForm1, usersSaveForm3);
//
//        RoomSaveForm form = RoomSaveForm.builder().name("test").users(users).build();
//        RoomSaveForm form2 = RoomSaveForm.builder().name("test2").users(users2).build();
//
//        Room room = roomApplication.createRoom(form);
//    }
//
//    @Test
//    void findRoom(){
//        Room room = roomService.findRoomById(1L);
//        System.out.println(room.getParticipantsList().size());
//        System.out.println(room.getParticipantsList().get(0).getUser().getName());
//    }
//}
