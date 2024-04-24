package com.example.chatapi.web.validations.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class ChatExceptionResponse {
    private String message;
    private ChatErrorCode errorCode;
}
