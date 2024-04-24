package com.example.accountapi.web.validation.form.customer;

import com.example.accountapi.web.validation.annotation.Tel;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Getter
public class CustomerUpdateForm {

    @NotBlank(message = "이메일을 입력해주세요")
    @Email(message = "이메일 형식에 맞게 입력해주세요")
    @Schema(description = "이메일", example = "tester@test.com")
    private String email;

    @NotBlank(message = "이름을 입력해주세요")
    @Schema(description = "이름", example = "nameCanChangeNow")
    private String name;

    @NotBlank(message = "비밀번호를 입력해주세요")
    @Schema(description = "비밀번호", example = "newPasswordButEncrypted")
    private String password;

    @NotNull(message = "생년월일을 입력해주세요(입력 형식: yyyy-MM-dd)")
    @Schema(description = "생년월일", example = "1999-01-01")
    private LocalDate birth;

    @NotBlank(message = "휴대폰 번호를 입력해주세요")
    @Tel(message = "-을 빼고 번호만 넣어 주세요")
    @Schema(description = "휴대폰 번호", example = "01000000000")
    private String phone;

    @Schema(description = "멤버십", example = "VIP")
    private String membership;


}
