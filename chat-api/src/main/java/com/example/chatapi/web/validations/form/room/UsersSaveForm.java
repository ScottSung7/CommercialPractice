package com.example.chatapi.web.validations.form.room;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsersSaveForm {

    @Schema(description = "사용자 이름", example = "tester")
    private String name;
    @Schema(description = "사용자 닉네임", example = "testerOnChat")
    private String nickname;
    @Schema(description = "사용자 이메일", example = "tester@test.com")
    private String email;
    @Schema(description = "사용자 유형" , example = "CUSTOMER")
    private String type;
}
