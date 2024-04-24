package com.example.chatapi.web.validations.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class ChatExceptionController {

    @ExceptionHandler({
            ChatException.class
    })
    public ResponseEntity<ChatExceptionResponse> orderBadRequestException(final ChatException e){

        log.warn("api Exception: {}", e.getErrorCode());
        return ResponseEntity.badRequest().body(new ChatExceptionResponse(e.getMessage(), e.getErrorCode()));
    }


}
