package com.example.accountapi.domain.dto;

import com.example.accountapi.domain.model.Customer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CustomerDto {

    @Schema(description = "고객 ID", example = "1")
    private Long id;

    @Schema(description = "이메일", example = "tester@test.com")
    private String email;

    @Schema(description = "이름", example = "tester")
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

    @Schema(description = "멤버십", example = "VIP")
    private String membership;

    @Schema(description = "잔액", example = "10000")
    private Integer balance;

    public static CustomerDto from (Customer customer){
        return CustomerDto.builder()
                .id(customer.getId())
                .email(customer.getEmail())
                .phone(customer.getPhone())
                .name(customer.getName())
                .password(customer.getPassword())
                .birth(customer.getBirth())
                .verificationExpiredAt(customer.getVerificationExpiredAt())
                .verified(customer.isVerified())
                .membership(customer.getMembership())
                .balance(customer.getBalance() == null ? 0 : customer.getBalance())
                .build();
    }

}
