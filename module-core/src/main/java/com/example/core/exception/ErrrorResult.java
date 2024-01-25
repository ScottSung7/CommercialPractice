package com.example.core.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class ErrrorResult {
    private int status;
    private HttpStatus error;
}
