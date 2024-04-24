package com.example.chatapi.model.dto.user;

import com.example.chatapi.model.entity.roomAndUser.Room;
import lombok.*;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RoomListDTO {

    private String id;

    private String name;

    public static RoomListDTO from(Room room) {
        return builder()
                .id(room.getId())
                .name(room.getName())
                .build();
    }




}
