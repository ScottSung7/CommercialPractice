package com.example.chatapi.config.common.validation.securityException;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class BasicExceptionResponse {
    private String message;
    private BasicErrorCode basicErrorCode;
}
