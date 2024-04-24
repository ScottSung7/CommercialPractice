package com.example.chatapi.model.entity.roomAndUser;

import com.example.chatapi.web.validations.form.room.UsersSaveForm;
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

    @Column(unique = true)
    private String email;

    private String type;


    @OneToMany(mappedBy="user")
    private final List<Participants> involvedRooms = new ArrayList<>();

//    @ManyToOne
//    @JoinColumn(name = "room_id")
//    private Room room;

    public static Users from(UsersSaveForm form) {
        return Users.builder()
                .name(form.getName())
                .nickname(form.getNickname() == null ? form.getName() : form.getNickname())
                .email(form.getEmail())
                .type(form.getType())
                .build();
    }
//    public static Users of(Participants participants, UsersSaveForm form) {
//        Users user = Users.builder()
//                .name(form.getName())
//                .email(form.getEmail())
//                .type(form.getType())
//                .build();
//        user.addRoom(participants);
//        return user;
//    }

    public void addRoom(Participants participants){
        this.involvedRooms.add(participants);
    }


}
