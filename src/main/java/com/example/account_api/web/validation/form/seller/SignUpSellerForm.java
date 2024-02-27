package com.example.account_api.web.validation.form.seller;

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
public class SignUpSellerForm {

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
    private String companyRegistrationNumber;
}
