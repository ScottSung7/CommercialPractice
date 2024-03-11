package com.example.accountapi.domain.dto;

import com.example.accountapi.domain.model.Customer;
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
    //hi
    private String email;
    private String name;
    private String phone;
    private String password;
    private LocalDate birth;

    private LocalDateTime verificationExpiredAt;
    private String verificationCode;
    private boolean verified;

    private String membership;

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
