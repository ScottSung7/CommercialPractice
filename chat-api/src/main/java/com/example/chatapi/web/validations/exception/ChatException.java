package com.example.chatapi.web.validations.exception;

import lombok.Getter;

@Getter
public class ChatException extends RuntimeException{
    private final ChatErrorCode errorCode;

    public ChatException(ChatErrorCode errorCode){
        super(errorCode.getDetail());
        this.errorCode = errorCode;
    }
}
