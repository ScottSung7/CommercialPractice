package com.example.accountapi.web.validation.form;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class ChangeBalanceForm {

    @NotBlank(message = "보내는 사람을 입력해주세요")
    @Schema(description = "보내는 사람", example = "sender")
    private String from;

    @Schema(description = "메시지", example = "입금")
    private String message;

    @NotNull(message = "금액을 입력해주세요")
    @Schema(description = "금액", example = "1000")
    private Integer money;
}
