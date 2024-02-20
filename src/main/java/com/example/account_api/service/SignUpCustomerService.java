package com.example.account_api.service;

import com.example.account_api.domain.model.Customer;
import com.example.account_api.web.validation.form.SignUpCustomerForm;

public interface SignUpCustomerService {

    public Customer signUp(SignUpCustomerForm form);
}
