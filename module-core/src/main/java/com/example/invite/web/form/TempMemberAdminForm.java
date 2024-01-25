package com.example.invite.web.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@Data
public class TempMemberAdminForm {

    @NotBlank
    private String name;
    @NotNull
    private Integer phoneNumber;
    @NotBlank
    private String email;





}
