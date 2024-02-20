package com.example.account_api.domain.model;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.envers.AuditOverride;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@AuditOverride(forClass = BaseEntity.class)
public class Seller extends BaseEntity{

    @Id
    @Column(name="seller_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String companyRegistrationNumber;
    @Column(name="seller_email", unique = true)
    private String email;
    private String name;
    private String phone;
    private String password;
    private LocalDate birth;

    /*private LocalDateTime verificationExpiredAt;
    private String verificationCode;
    private boolean verified;*/


}
