package com.example.account_api.web.validation.exception;

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
    public ResponseEntity<ExceptionResponse> accountBadRequestException(final AccountException e){


        log.warn("api Exception: {}", e.getErrorCode());
        return ResponseEntity.badRequest().body(new ExceptionResponse(e.getMessage(), e.getErrorCode()));
    }
    //이게 아니라 bad request로 통합 된건가?
//    @ExceptionHandler({
//            AccountException.class
//    })
//    public ResponseEntity<ExceptionResponse> accountInternalServerErrorException(final AccountException e){
//        log.warn("api Exception: {}", e.getErrorCode());
//        return ResponseEntity.internalServerError().body(new ExceptionResponse(e.getMessage(), e.getErrorCode()));
//    }


}
