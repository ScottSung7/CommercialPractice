package com.example.accountapi.web;


import com.example.accountapi.web.validation.form.customer.CustomerSignUpForm;
import com.example.accountapi.web.validation.form.customer.CustomerUpdateForm;
import com.example.accountapi.web.validation.form.seller.SellerSignUpForm;

import java.time.LocalDate;


public class Tester {


    public static CustomerSignUpForm customerSignUpForm = CustomerSignUpForm.builder()
            .email("sungscott@naver.com")
            .name("tester")
            .phone("1234")
            .password("1122")
            .birth(LocalDate.now())
            .build();
    public static CustomerUpdateForm customerUpdateForm = CustomerUpdateForm.builder()
            .email("sungscott@naver.com")
            .name("tester")
            .phone("0000")
            .password("1122")
            .birth(LocalDate.now())
            .build();

    public static SellerSignUpForm sellerSignUpForm = SellerSignUpForm.builder()
            .email("sungscott@naver.com")
            .name("tester")
            .phone("1234")
            .password("1122")
            .birth(LocalDate.now())
            .build();

}
