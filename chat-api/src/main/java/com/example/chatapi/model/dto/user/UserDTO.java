package com.example.chatapi.model.dto.user;

import com.example.chatapi.model.entity.roomAndUser.Users;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserDTO {

    private Long id;
    private String nickname;
    private String email;
    private String type;

    private List<RoomListDTO> roomList;

    public static UserDTO from(Users user) {
        return builder()
                .id(user.getId())
                .nickname(user.getNickname())
                .email(user.getEmail())
                .type(user.getType())
                .roomList(user.getInvolvedRooms().stream()
                                .map(involved -> RoomListDTO.from(involved.getRoom()))
                                        .collect(Collectors.toList()))
                .build();
    }
}
