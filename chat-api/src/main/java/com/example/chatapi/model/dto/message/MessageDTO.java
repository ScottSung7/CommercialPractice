package com.example.chatapi.model.dto.message;

import com.example.chatapi.model.entity.message.Message;
import lombok.*;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MessageDTO {

    private String nickname;

    private String content;

    public static MessageDTO from(Message message) {
        return builder()
                .nickname(message.getNickname())
                .content(message.getContent())
                .build();
    }




}
