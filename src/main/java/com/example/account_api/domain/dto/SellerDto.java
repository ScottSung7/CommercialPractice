package com.example.account_api.domain.dto;

import com.example.account_api.domain.model.Customer;
import com.example.account_api.domain.model.Seller;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class SellerDto {

    private String companyRegistrationNumber;
    private Long id;

    private String email;
    private String name;
    private String phone;
    private String password;
    private LocalDate birth;

    private LocalDateTime verificationExpiredAt;
    private String verificationCode;
    private boolean verified;


    public static SellerDto from (Seller seller){
        return SellerDto.builder()
                .id(seller.getId())
                .email(seller.getEmail())
                .password(seller.getPassword())
                .phone(seller.getPhone())
                .name(seller.getName())
                .birth(seller.getBirth())
                .verificationExpiredAt(seller.getVerificationExpiredAt())
                .verified(seller.isVerified())
                .companyRegistrationNumber(seller.getCompanyRegistrationNumber())
                .build();
    }

}

