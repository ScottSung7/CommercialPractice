package com.example.account_api.application.service;

import com.example.account_api.domain.model.Customer;
import com.example.account_api.web.validation.form.SignUpCustomerForm;

import java.time.LocalDateTime;
import java.util.Optional;

public interface SignUpCustomerService {

    public Customer signUp(SignUpCustomerForm form);

    Optional<Customer> isEmailExist(String email);

    LocalDateTime changeCustomerValidateEmail(Long id, String code);
}
