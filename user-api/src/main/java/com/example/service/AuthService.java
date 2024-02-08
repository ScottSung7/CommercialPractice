package com.example.service;

import com.example.controller.dto.EmailCertificationRequestDto;
import org.springframework.http.ResponseEntity;

public interface AuthService {

    ResponseEntity<EmailCertificationRequestDto> emailCertification(EmailCertificationRequestDto dto);

}
