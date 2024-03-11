package com.example.accountapi.web.validation.form;

import lombok.Getter;

@Getter
public class ChangeBalanceForm {
    private String from;
    private String message;
    private Integer money;
}
