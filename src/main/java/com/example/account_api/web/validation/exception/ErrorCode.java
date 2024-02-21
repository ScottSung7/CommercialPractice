package com.example.account_api.web.validation.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    UNKNOWN_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "알 수 없는 에러가 발생하였습니다."),
    VERIFICATION_EMAIL_ERROR(HttpStatus.BAD_REQUEST, "인증 메일 전송 중 에러가 발생하였습니다."),
    NOT_FOUND_USER(HttpStatus.BAD_REQUEST, "존재하지 않는 유저 입니다."),
    ALREADY_REGISTER_USER(HttpStatus.BAD_REQUEST, "이미 존재하는 유저입니다.");
    private final HttpStatus httpStatus;
    private final String detail;

}
