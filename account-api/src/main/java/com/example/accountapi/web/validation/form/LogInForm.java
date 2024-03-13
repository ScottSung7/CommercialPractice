package com.example.accountapi.web.validation.form;

import lombok.Data;

@Data
public class LogInForm {

    private String email;
    private String password;
}
