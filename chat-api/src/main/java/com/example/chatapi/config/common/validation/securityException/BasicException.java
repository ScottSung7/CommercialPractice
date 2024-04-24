package com.example.chatapi.config.common.validation.securityException;

import lombok.Getter;

@Getter
public class BasicException extends RuntimeException{
    private final BasicErrorCode basicErrorCode;

    public BasicException(BasicErrorCode basicErrorCode){
        super(basicErrorCode.getDetail());
        this.basicErrorCode = basicErrorCode;
    }
}
