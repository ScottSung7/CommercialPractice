package com.example.service;

import com.example.config.provider.EmailProvider;
import com.example.controller.dto.EmailCertificationRequestDto;
import com.example.controller.dto.EmailCertificationResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImplement implements AuthService{

    private final EmailProvider emailProvider;

    @Override
    public ResponseEntity<EmailCertificationRequestDto> emailCertification(EmailCertificationRequestDto dto) {
        try {
            String userId = dto.getId();
            String email = dto.getEmail();

            boolean isSuccessed = emailProvider.sendCertificationMail("hgssung@gmail.com", "123");
            if(!isSuccessed){
                System.out.println("fail");
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("failll");
            return null;
        }
        return EmailCertificationResponseDto.success();
    }
}
