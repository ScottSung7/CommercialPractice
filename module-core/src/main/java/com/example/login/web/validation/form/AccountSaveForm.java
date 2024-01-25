package com.example.login.web.validation.form;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AccountSaveForm {

    @NotBlank
    private String name;

    @NotBlank
    private String password;

    @NotBlank
    private String email;

    @NotBlank
    private String phoneNumber;


}
