package com.example.chatapi.web.validations.form.room;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class RoomAddUserForm extends RoomSaveForm{

    @Schema(description = "채팅방 ID", example = "invite로 룸 추가 후 id를 넣어 주세요.")
    private String roomId;

}
