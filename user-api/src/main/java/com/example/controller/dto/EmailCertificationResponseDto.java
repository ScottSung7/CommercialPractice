package com.example.controller.dto;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class EmailCertificationResponseDto {

    private EmailCertificationResponseDto(){

    }

    public static ResponseEntity<EmailCertificationRequestDto> success(){
        EmailCertificationResponseDto responseBody = new EmailCertificationResponseDto();
        return ResponseEntity.status(HttpStatus.OK).build();
    }



}
