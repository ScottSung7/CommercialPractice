package com.example.account_api.domain.dto;

import com.example.account_api.domain.model.Customer;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class CustomerDto {

    private Long id;

    private String email;
    private String name;
    private String phone;
    private String password;
    private LocalDate birth;

    private LocalDateTime verificationExpiredAt;
    private String verificationCode;
    private boolean verified;

    private String membership;

    public static CustomerDto from (Customer customer){
        return CustomerDto.builder()
                .id(customer.getId())
                .email(customer.getEmail())
                .password(customer.getPassword())
                .birth(customer.getBirth())
                .verificationExpiredAt(customer.getVerificationExpiredAt())
                .verified(customer.isVerified())
                .membership(customer.getMembership())
                .build();
    }

}
