package com.example.chatapi.model.room;

import com.example.chatapi.web.RoomSaveForm;
import com.example.chatapi.web.UsersSaveForm;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.stream.Collectors;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomUserList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
//
//    @ManyToOne
//    @JoinColumn(name = "user_id")
//    private Users user;
//
//    @ManyToOne
//    @JoinColumn(name = "room_id")
//    private Room room;

//    public static RoomUserList from(UsersSaveForm form) {
//        return RoomUserList.builder()
//                .user(Users.from(form))
//                .build();
//    }
}
