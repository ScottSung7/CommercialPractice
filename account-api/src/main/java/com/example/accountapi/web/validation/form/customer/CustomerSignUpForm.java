package com.example.accountapi.web.validation.form.customer;

import com.example.accountapi.web.validation.annotation.Tel;
import com.example.accountapi.web.validation.annotation.validator.TelValidator;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Constraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerSignUpForm {


    @Email(message = "이메일 형식에 맞게 입력해주세요")
    @NotBlank(message = "이메일을 입력해주세요")
    @Schema(description = "이메일", example = "이메일을 입력해 주세요.")
    private String email;

    @NotBlank(message = "이름을 입력해주세요")
    @Schema(description = "이름", example = "tester")
    private String name;

    @NotBlank(message = "비밀번호를 입력해주세요")
    @Schema(description = "비밀번호", example = "1234")
    private String password;

    @NotNull(message = "생년월일을 입력해주세요(입력 형식: yyyy-MM-dd)")
    @Schema(description = "생년월일", example = "1999-01-01")
    private LocalDate birth;

    @NotNull(message = "휴대폰 번호를 입력해주세요")
    @Tel(message = "-을 빼고 번호만 넣어 주세요")
    @Schema(description = "휴대폰 번호", example = "01012345678")
    private String phone;
}