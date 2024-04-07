package com.example.orderapi.config.common.validation.accountException;

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
