package com.example.account_api.application.service.signIn.customer;

import com.example.account_api.domain.model.Customer;
import com.example.account_api.web.validation.form.customer.SignUpCustomerForm;
import com.example.account_api.web.validation.form.customer.UpdateCustomerForm;

public interface SignUpCustomerService {

    Customer signUp(SignUpCustomerForm form);

    boolean isEmailExist(String email);

    Customer changeCustomerValidateEmail(Customer SignUpCustomer, String code);

    Customer update(Customer customer);
}
