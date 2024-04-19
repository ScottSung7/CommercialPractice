package com.example.chatapi.model.room;

import com.example.chatapi.web.RoomSaveForm;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

//    @OneToMany(mappedBy = "room")
//    private List<RoomUserList> roomUser = new ArrayList<>();

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    private List<Users> users;

    public static Room from(RoomSaveForm form) {
        return Room.builder()
                .name(form.getName())
                .users(form.getUsers().stream().map(Users::from).collect(Collectors.toList()))
                .build();
    }


}
