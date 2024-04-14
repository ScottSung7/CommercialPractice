package com.example.accountapi.web.validation.form;

import lombok.Data;
import lombok.Getter;

@Getter
public class LogInForm {

    private String email;
    private String password;
    private String type;
}
