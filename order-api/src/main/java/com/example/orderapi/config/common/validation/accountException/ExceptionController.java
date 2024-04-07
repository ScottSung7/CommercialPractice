package com.example.orderapi.config.common.validation.accountException;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class ExceptionController {

    @ExceptionHandler({
            AccountException.class
    })
    public ResponseEntity<ExceptionResponse> orderBadRequestException(final AccountException e){


        log.warn("api Exception: {}", e.getErrorCode());
        return ResponseEntity.badRequest().body(new ExceptionResponse(e.getMessage(), e.getErrorCode()));
    }


}
