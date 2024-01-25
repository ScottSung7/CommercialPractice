package com.example.core.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionControllerAdvice {
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ErrrorResult DataIntegrityViolationException(DataIntegrityViolationException ex){
        return new ErrrorResult(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST);
    }


}
