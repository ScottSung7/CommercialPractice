package com.example.account_api.domain.model;


import com.example.account_api.web.validation.form.customer.SignUpCustomerForm;
import com.example.account_api.web.validation.form.seller.SignUpSellerForm;
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


    public static Seller from(SignUpSellerForm form){
        return Seller.builder()
                .email(form.getEmail().toLowerCase(Locale.ROOT))
                .password(form.getPassword())
                .name(form.getName())
                .birth(form.getBirth())
                .phone(form.getPhone())
                .build();
    }

}
