package com.example.account_api.web.validation.exception;

import lombok.Getter;

@Getter
public class AccountException extends RuntimeException{
    private final ErrorCode errorCode;

    public AccountException(ErrorCode errorCode){
        super(errorCode.getDetail());
        this.errorCode = errorCode;
    }
}
