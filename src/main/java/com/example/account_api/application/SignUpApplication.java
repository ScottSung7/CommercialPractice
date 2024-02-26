package com.example.account_api.application;

import com.example.account_api.web.validation.form.customer.SignUpCustomerForm;


public interface SignUpApplication {
    public String customerSignUp(SignUpCustomerForm signUpCustomerForm);
}

