package com.example.chatapi.model.room;

import com.example.chatapi.web.UsersSaveForm;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String nickname;

    private String name;

    private String email;

    private String type;

//
//    @OneToMany(mappedBy="user")
//    private List<RoomUserList> roomUser = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    public static Users of(String nickname, String name, String email, String type) {
        return Users.builder()
                .nickname(nickname)
                .name(name)
                .email(email)
                .type(type)
                .build();
    }

    public static Users from(UsersSaveForm form) {
        return Users.builder()
                .name(form.getName())
                .email(form.getEmail())
                .type(form.getType())
                .build();
    }


}
