package com.example.account_api.service;

import com.example.account_api.domain.model.Customer;
import com.example.account_api.web.validation.form.SignUpCustomerForm;

import java.util.Optional;

public interface SignUpCustomerService {

    public Optional<Customer> signUp(SignUpCustomerForm form);
}
