package com.example.accountapi.domain.model;


import com.example.accountapi.web.validation.form.customer.CustomerUpdateForm;
import com.example.accountapi.web.validation.form.seller.SellerSignUpForm;
import com.example.accountapi.web.validation.form.seller.SellerUpdateForm;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.envers.AuditOverride;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Locale;

@Entity
@Getter
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AuditOverride(forClass = BaseEntity.class)
public class Seller extends BaseEntity{

    @Id
    @Column(name="seller_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="seller_email", unique = true)
    private String email;
    private String name;
    private String phone;
    private String password;
    private LocalDate birth;

    private LocalDateTime verificationExpiredAt;
    @ColumnDefault(value = "false")
    private boolean verified;

    private String companyRegistrationNumber;
    @Builder
    public Seller(String email, String password, String name, LocalDate birth, String phone, LocalDateTime verificationExpiredAt, String companyRegistrationNumber, Long id) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.birth = birth;
        this.phone = phone;
        this.verificationExpiredAt = verificationExpiredAt;

        //optional
        this.companyRegistrationNumber = companyRegistrationNumber;
        //In need
        this.id = id;
    }

    public static Seller from(SellerSignUpForm form){
        return Seller.builder()
                .email(form.getEmail().toLowerCase(Locale.ROOT))
                .password(form.getPassword())
                .name(form.getName())
                .birth(form.getBirth())
                .phone(form.getPhone())
                .verificationExpiredAt(LocalDateTime.now())
                .build();
    }

    public static Seller updateFrom(SellerUpdateForm form, Seller seller) {
        seller.setName(form.getName());
        seller.setBirth(form.getBirth());
        seller.setPhone(form.getPhone());
        seller.setCompanyRegistrationNumber(form.getCompanyRegistrationNumber());
        return seller;
    }

    //Additional Method
    public void encodePassword(String encodedPassword){
        this.password = encodedPassword;
    }
    public void addVerificationExpirationDate(LocalDateTime expirationDate) {
        this.verificationExpiredAt = expirationDate;
    }
    public boolean verifySeller(boolean verified) {
        this.verified = verified;
        return this.verified;
    }
}
