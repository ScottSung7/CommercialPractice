package com.example.account_api.domain.model;


import com.example.account_api.web.validation.form.SignUpCustomerForm;
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
public class Customer extends BaseEntity{

    @Id
    @Column(name="customer_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;
    private String name;
    private String phone;
    private String password;
    private LocalDate birth;

    /*private LocalDateTime verificationExpiredAt;
    private String verificationCode;
    private boolean verified;*/

    public static Customer from(SignUpCustomerForm form){
        return Customer.builder()
                .email(form.getEmail().toLowerCase(Locale.ROOT))
                .password(form.getPassword())
                .name(form.getName())
                .birth(form.getBirth())
                .phone(form.getPhone())
                .build();
    }


}
