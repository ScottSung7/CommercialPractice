package com.example.accountapi.domain.model;


import com.example.accountapi.web.validation.form.seller.SellerSignUpForm;
import com.example.accountapi.web.validation.form.seller.SellerUpdateForm;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.envers.AuditOverride;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Locale;

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

    private LocalDateTime verificationExpiredAt;
    private String verificationCode;
    private boolean verified;


    public static Seller from(SellerSignUpForm form){
        return Seller.builder()
                .email(form.getEmail().toLowerCase(Locale.ROOT))
                .password(form.getPassword())
                .name(form.getName())
                .birth(form.getBirth())
                .phone(form.getPhone())
                .verified(false)
                .build();
    }

    public static Seller updateFrom(SellerUpdateForm form, Seller seller) {
        seller.setName(form.getName());
        seller.setBirth(form.getBirth());
        seller.setPhone(form.getPhone());
        seller.setCompanyRegistrationNumber(form.getCompanyRegistrationNumber());
        return seller;
    }
}
