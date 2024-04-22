package com.example.chatapi.model.entity.roomAndUser;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Participants {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

   public static Participants of(Room room, Users user){
        Participants list =  Participants.builder()
                .room(room)
                .user(user)
                .build();
        return list;
    }
    @Override
    public String toString() {
        return "Participants{" +
                "id=" + id +
                ", user=" + user.toString() +
                ", room=" + room.toString() +
                '}';
    }
}
