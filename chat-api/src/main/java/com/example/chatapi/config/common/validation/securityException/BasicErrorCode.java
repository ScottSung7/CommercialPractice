package com.example.chatapi.config.common.validation.securityException;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum BasicErrorCode {

    //Account
    VERIFICATION_EXPIRED(HttpStatus.BAD_REQUEST, "만료된 인증 입니다."),

    //Login
    NO_TOKEN(HttpStatus.BAD_REQUEST, "로그인 토큰이 없습니다."),
    TOKEN_EXPIRED(HttpStatus.BAD_REQUEST, "만료된 토큰입니다"),
    LOGIN_TYPE_NOT_EXIST(HttpStatus.BAD_REQUEST, "존재하지 않는 유형의 유저 입니다."),
    LOGIN_FAILED(HttpStatus.BAD_REQUEST, "로그인 인증에 실패하였습니다."),
    LOGIN_TYPE_ERROR(HttpStatus.BAD_REQUEST, "현재 권한으로 접근 불가한 요청입니다."),
    NOT_LOGIN_ERROR(HttpStatus.BAD_REQUEST, "로그인 여부를 확인해 주세요"),
    NO_LOGIN_PLEASE(HttpStatus.BAD_REQUEST, "로그아웃후 이용 가능합니다.");



    private final HttpStatus httpStatus;
    private final String detail;

}
