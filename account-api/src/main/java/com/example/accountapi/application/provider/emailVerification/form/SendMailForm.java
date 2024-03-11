package com.example.accountapi.application.provider.emailVerification.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SendMailForm {
    private String from;
    private String to;
    private String subject;
    private String text;
    private String code;
}
