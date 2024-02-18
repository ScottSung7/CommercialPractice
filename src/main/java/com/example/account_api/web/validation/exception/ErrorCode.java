package com.example.account_api.web.validation.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    UNKNOWN_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "알 수 없는 에러가 발생하였습니다.");

    private final HttpStatus httpStatus;
    private final String detail;

}
