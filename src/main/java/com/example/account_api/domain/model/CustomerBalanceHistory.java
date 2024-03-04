package com.example.account_api.domain.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerBalanceHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(targetEntity = Customer.class, fetch = FetchType.LAZY)
    private Customer customer;

    //변경된 돈
    private Integer changedMoney;

    //현재 돈.
    private Integer currentMoney;

    private String fromMessage;

    private String description;
}
