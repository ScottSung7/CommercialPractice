package com.example.chatapi.web.validations.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ChatErrorCode {

    //Account
    UNKNOWN_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "알 수 없는 에러가 발생하였습니다."),
    VERIFICATION_EMAIL_ERROR(HttpStatus.BAD_REQUEST, "인증 메일 전송 중 에러가 발생하였습니다."),
    NOT_FOUND_USER(HttpStatus.INTERNAL_SERVER_ERROR, "존재하지 않는 유저 입니다."),
    ALREADY_REGISTER_USER(HttpStatus.BAD_REQUEST, "이미 존재하는 유저입니다."),
    NOT_LOGIN_ERROR(HttpStatus.BAD_REQUEST, "로그인 여부를 확인해 주세요"),
    ALREADY_VERIFIED(HttpStatus.BAD_REQUEST, "이미 인증된 유저 입니다."),
    VERIFICATION_EXPIRED(HttpStatus.BAD_REQUEST, "만료된 인증 입니다."),
    NO_LOGIN_PLEASE(HttpStatus.BAD_REQUEST, "로그아웃후 이용 가능합니다."),

    //Chat - Room
    ROOM_ALREADY_EXIST(HttpStatus.BAD_REQUEST, "이미 존재하는 방입니다."),
    ROOM_NOT_FOUND(HttpStatus.BAD_REQUEST, "존재하지 않는 방입니다."),

    //Chat - User
    USER_NOT_FOUND(HttpStatus.BAD_REQUEST, "존재하지 않는 유저입니다."),
    USER_ALREADY_EXIST(HttpStatus.BAD_REQUEST, "이미 존재하는 유저입니다."),

    //Login
    NO_TOKEN(HttpStatus.BAD_REQUEST, "로그인 토큰이 없습니다."),
    TOKEN_EXPIRED(HttpStatus.BAD_REQUEST, "만료된 토큰입니다"),
    LOGIN_TYPE_NOT_EXIST(HttpStatus.BAD_REQUEST, "존재하지 않는 유형의 유저 입니다."),
    LOGIN_FAILED(HttpStatus.BAD_REQUEST, "로그인 인증에 실패하였습니다."),
    LOGIN_TYPE_ERROR(HttpStatus.BAD_REQUEST, "현재 권한으로 접근 불가한 요청입니다."),





    INVALID_DECRYPT_ERROR(HttpStatus.BAD_REQUEST, "복호화에 실패하였습니다."),

    TOKEN_PARSING(HttpStatus.BAD_REQUEST, "로그인 토큰 등록에 실패 하였습니다.");




    private final HttpStatus httpStatus;
    private final String detail;

}
