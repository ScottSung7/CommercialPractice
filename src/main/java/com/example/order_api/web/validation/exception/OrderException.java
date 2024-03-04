package com.example.order_api.web.validation.exception;

import lombok.Getter;

@Getter
public class OrderException extends RuntimeException{
    private final OrderErrorCode orderErrorCode;

    public OrderException(OrderErrorCode orderErrorCode){
        super(orderErrorCode.getDetail());
        this.orderErrorCode = orderErrorCode;
    }
}
