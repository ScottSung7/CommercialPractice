package com.example.accountapi.domain.model;


import com.example.accountapi.web.validation.form.customer.CustomerSignUpForm;
import com.example.accountapi.web.validation.form.customer.CustomerUpdateForm;
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

    private LocalDateTime verificationExpiredAt;
    private String verificationCode;
    private boolean verified;

    private String membership;

    @Column(columnDefinition = "int default 0")
    private Integer balance;

    public static Customer from(CustomerSignUpForm form){
        return Customer.builder()
                .email(form.getEmail().toLowerCase(Locale.ROOT))
                .password(form.getPassword())
                .name(form.getName())
                .birth(form.getBirth())
                .phone(form.getPhone())
                .verified(false)
                .build();
    }

    public static Customer updateFrom(CustomerUpdateForm form, Customer customer){
        customer.setName(form.getName());
        customer.setBirth(form.getBirth());
        customer.setPhone(form.getPhone());
        customer.setMembership(form.getMembership());
        return customer;
    }


}
