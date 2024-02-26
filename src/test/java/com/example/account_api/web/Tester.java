package com.example.account_api.web;

import com.example.account_api.web.validation.form.customer.SignUpCustomerForm;

import java.time.LocalDate;


public class Tester {


    public static SignUpCustomerForm signUpCustomerForm = SignUpCustomerForm.builder()
            .email("sungscott@naver.com")
            .name("tester")
            .phone("1234")
            .password("1122")
            .birth(LocalDate.now())
            .build();


}
