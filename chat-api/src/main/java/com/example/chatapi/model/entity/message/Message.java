package com.example.chatapi.model.entity.message;

import com.example.chatapi.model.entity.roomAndUser.Room;
import com.example.chatapi.model.entity.roomAndUser.Users;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Message {

    @Id
    @GeneratedValue(generator = "uuid")
    public Long id;

    public String email;

    public String type;

    public String nickname;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    String content;

    public static Message of(Room room, Users user, String content) {
        return Message.builder()
                .email(user.getEmail())
                .type(user.getType())
                .nickname(user.getNickname())
                .room(room)
                .content(content)
                .build();
    }


//    @ManyToOne(fetch = FetchType.LAZY)
//    FilePath filePath;

}
