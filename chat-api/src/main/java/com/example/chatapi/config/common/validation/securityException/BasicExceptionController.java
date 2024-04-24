package com.example.chatapi.config.common.validation.securityException;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class BasicExceptionController {

    @ExceptionHandler({
            BasicException.class
    })
    public ResponseEntity<BasicExceptionResponse> basicBadRequestException(final BasicException e){

        log.warn("api Exception: {}", e.getBasicErrorCode());
        return ResponseEntity.badRequest().body(new BasicExceptionResponse(e.getMessage(), e.getBasicErrorCode()));
    }


}
