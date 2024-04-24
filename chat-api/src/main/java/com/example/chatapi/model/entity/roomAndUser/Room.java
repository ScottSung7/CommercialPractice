package com.example.chatapi.model.entity.roomAndUser;

import com.example.chatapi.model.entity.message.Message;
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
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "room_id")
    private String id;

    private String name;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Participants> participantsList = new ArrayList<>();

    @OneToMany(mappedBy = "room")
    private final List<Message> messages = new ArrayList<>();

    public static Room from(String name) {
        return Room.builder()
                .name(name)
                .build();
    }
//    public static Room from (RoomSaveForm form){
//        Room room = Room.builder()
//                .name(form.getName())
//                .build();
//
//        List<Participants> participants = form.getUsers().stream()
//                .map(user -> Participants.of(room, user))
//                .collect(Collectors.toList());
//        room.addUsers(participants);
//        return room;
//    }

    public void addParticipants(List<Participants> participants){
        participantsList.addAll(participants);
    }



}
