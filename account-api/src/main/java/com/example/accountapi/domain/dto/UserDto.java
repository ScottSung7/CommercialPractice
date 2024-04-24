package com.example.accountapi.domain.dto;

import com.example.accountapi.domain.model.Customer;
import com.example.accountapi.domain.model.Seller;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserDto {


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

    @Schema(description = "유저 타입", example = "CUSTOMER")
    private String type;


    public static UserDto from (Customer customer){
        return UserDto.builder()
                .email(customer.getEmail())
                .phone(customer.getPhone())
                .name(customer.getName())
                .password(customer.getPassword())
                .birth(customer.getBirth())
                .type("CUSTOMER")
                .build();
    }
    public static UserDto from(Seller seller){
        return UserDto.builder()
                .email(seller.getEmail())
                .phone(seller.getPhone())
                .name(seller.getName())
                .password(seller.getPassword())
                .birth(seller.getBirth())
                .type("SELLER")
                .build();
    }

}
