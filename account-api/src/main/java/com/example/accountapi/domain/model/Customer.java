package com.example.accountapi.domain.model;


import com.example.accountapi.web.validation.form.customer.CustomerSignUpForm;
import com.example.accountapi.web.validation.form.customer.CustomerUpdateForm;
import feign.Client;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.envers.AuditOverride;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Locale;

import static java.time.LocalDateTime.now;

@Entity
@Getter
@Setter(AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AuditOverride(forClass = BaseEntity.class)
public class
Customer extends BaseEntity{

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
    @ColumnDefault(value = "false")
    private boolean verified;

    private String membership;

    @Column(columnDefinition = "int default 0")
    private Integer balance = 0;

    @Builder
    public Customer(String email, String name, String phone, String password, LocalDate birth, LocalDateTime verificationExpiredAt, String membership, Long id) {
        this.email = email;
        this.name = name;
        this.phone = phone;
        this.password = password;
        this.birth = birth;
        this.verificationExpiredAt = verificationExpiredAt;

        //optional
        this.membership = membership;
        //In need.
        this.id = id;
    }
    //To Entity
    public static Customer from(CustomerSignUpForm form){
        return Customer.builder()
                .email(form.getEmail().toLowerCase(Locale.ROOT))
                .password(form.getPassword())
                .name(form.getName())
                .birth(form.getBirth())
                .phone(form.getPhone())
                .verificationExpiredAt(LocalDateTime.now())
                .build();
    }
    public static Customer updateFrom(CustomerUpdateForm form, Customer customer){

        customer.setName(form.getName());
        customer.setBirth(form.getBirth());
        customer.setPhone(form.getPhone());
        customer.setMembership(form.getMembership());
        return customer;
    }

    //Additional Method
    public void encodePassword(String encodedPassword){

        this.password = encodedPassword;
    }

    public void addVerificationExpirationDate(LocalDateTime expirationDate) {
        this.verificationExpiredAt = expirationDate;
    }
    public boolean verifyCustomer(boolean verified) {
        this.verified = verified;
        return this.verified;
    }
}
