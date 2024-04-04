package com.example.accountapi.domain.dto;

import com.example.accountapi.domain.model.Seller;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class SellerDto {

    @Schema(description = "판매자 ID", example = "1")
    private Long id;

    @Schema(description = "이메일", example = "testseller@test.com")
    private String email;

    @Schema(description = "이름", example = "testseller")
    private String name;

    @Schema(description = "휴대폰 번호", example = "01012345678")
    private String phone;

    @Schema(description = "비밀번호", example = "1234")
    private String password;

    @Schema(description = "생년월일", example = "1999-01-01")
    private LocalDate birth;

    @Schema(description = "인증 만료 시간", example = "2021-08-01T00:00:00")
    private LocalDateTime verificationExpiredAt;

    @Schema(description = "인증 여부", example = "true")
    private boolean verified;

    @Schema(description = "사업자 등록번호", example = "123-45-67890")
    private String companyRegistrationNumber;


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

