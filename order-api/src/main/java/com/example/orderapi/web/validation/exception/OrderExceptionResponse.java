package com.example.orderapi.web.validation.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class OrderExceptionResponse {
    private String message;
    private OrderErrorCode orderErrorCode;
}
