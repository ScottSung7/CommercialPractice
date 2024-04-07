package com.example.orderapi.config.common.validation.accountException;

import lombok.Getter;

@Getter
public class AccountException extends RuntimeException{
    private final ErrorCode errorCode;

    public AccountException(ErrorCode errorCode){
        super(errorCode.getDetail());
        this.errorCode = errorCode;
    }
}
