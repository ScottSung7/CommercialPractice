package com.example.account_api.application.service.signIn;

import com.example.account_api.domain.model.Customer;
import com.example.account_api.web.validation.form.customer.SignUpCustomerForm;

public interface SignUpCustomerService {

    Customer signUp(SignUpCustomerForm form);

    boolean isEmailExist(String email);

    Customer changeCustomerValidateEmail(Customer SignUpCustomer, String code);
}
