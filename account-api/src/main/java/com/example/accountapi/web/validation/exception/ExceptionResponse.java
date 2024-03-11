package com.example.accountapi.web.validation.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class ExceptionResponse {
    private String message;
    private ErrorCode errorCode;
}
