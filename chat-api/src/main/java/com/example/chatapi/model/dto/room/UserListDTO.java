package com.example.chatapi.model.dto.room;

import com.example.chatapi.model.entity.roomAndUser.Users;
import lombok.*;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserListDTO {

    private Long id;
    private String nickname;
    private String email;
    private String type;

    public static UserListDTO from(Users user) {
        return builder()
                .id(user.getId())
                .nickname(user.getNickname())
                .email(user.getEmail())
                .type(user.getType())
                .build();
    }

}
