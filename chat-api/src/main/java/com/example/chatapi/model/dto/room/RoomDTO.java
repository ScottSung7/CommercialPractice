package com.example.chatapi.model.dto.room;

import com.example.chatapi.model.entity.roomAndUser.Room;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;


@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RoomDTO {

    private String id;

    private String name;

    private List<UserListDTO> users;

    public static RoomDTO from(Room room) {
        return builder()
                .id(room.getId())
                .name(room.getName())
                .users(room.getParticipantsList().stream()
                        .map(par -> UserListDTO.from(par.getUser()))
                        .collect(Collectors.toList()))
                .build();
    }
}
