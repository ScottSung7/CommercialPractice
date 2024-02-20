package com.example.account_api.web;

import com.example.account_api.web.validation.form.SignUpCustomerForm;
import lombok.Getter;

import java.time.LocalDate;


public class Tester {


    public static SignUpCustomerForm signUpCustomerForm = SignUpCustomerForm.builder()
            .email("tester's email")
            .name("tester")
            .phone("1234")
            .password("1122")
            .birth(LocalDate.now())
            .build();


}
