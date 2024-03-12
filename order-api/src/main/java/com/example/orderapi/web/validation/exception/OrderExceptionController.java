package com.example.orderapi.web.validation.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class OrderExceptionController {

    @ExceptionHandler({
            OrderException.class
    })
    public ResponseEntity<OrderExceptionResponse> orderBadRequestException(final OrderException e){


        log.warn("api Exception: {}", e.getOrderErrorCode());
        
        return ResponseEntity.badRequest().body(new OrderExceptionResponse(e.getMessage(), e.getOrderErrorCode()));
    }


}
