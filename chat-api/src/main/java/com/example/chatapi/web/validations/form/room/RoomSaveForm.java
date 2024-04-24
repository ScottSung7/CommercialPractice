package com.example.chatapi.web.validations.form.room;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class RoomSaveForm {

    @Schema(description = "방 이름", example = "테스트 방")
    private String name;

    @Schema(name="usersSaveForms", description = "참여자 리스트")
    private List<UsersSaveForm> usersSaveForms;

}
