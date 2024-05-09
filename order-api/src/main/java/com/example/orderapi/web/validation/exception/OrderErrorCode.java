package com.example.orderapi.web.validation.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum OrderErrorCode {

    //Product
    NOT_FOUND_PRODUCT(HttpStatus.BAD_REQUEST, "상품을 찾을 수 없습니다."),
    NO_PRODUCT(HttpStatus.BAD_REQUEST, "등록된 상품이 없습니다."),

    //Item
    NOT_FOUND_ITEM (HttpStatus.BAD_REQUEST, "아이템을 찾을 수 없습니다."),
    SAME_ITEM_NAME(HttpStatus.BAD_REQUEST, "아이템명 중복입니다."),
    //Cart
    CART_CHANGE_FAIL(HttpStatus.BAD_REQUEST, "장바구니에 추가할 수 없습니다."),
    ITEM_COUNT_NOT_ENOUGH(HttpStatus.BAD_REQUEST, "상품의 수량이 부족합니다"),

    WORKING_ON(HttpStatus.BAD_REQUEST, "작업중입니다."),
    SAME_PRODUCT_NAME(HttpStatus.BAD_REQUEST, "같은 이름의 제품이 있습니다.");



    private final HttpStatus httpStatus;
    private final String detail;

}
