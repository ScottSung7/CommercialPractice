package com.example.accountapi.web.validation.form.customer;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
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
public class SignUpCustomerForm {

    @NotBlank
    private String email;
    @NotBlank
    private String name;
    @NotBlank
    private String password;
    @NotNull
    private LocalDate birth;
    @NotBlank
    private String phone;
}
