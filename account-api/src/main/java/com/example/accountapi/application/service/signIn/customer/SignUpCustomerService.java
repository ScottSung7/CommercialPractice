package com.example.accountapi.application.service.signIn.customer;

import com.example.accountapi.domain.model.Customer;
import com.example.accountapi.web.validation.form.customer.SignUpCustomerForm;
import com.example.accountapi.web.validation.form.customer.UpdateCustomerForm;

public interface SignUpCustomerService {

    public Customer signUp(SignUpCustomerForm form);

    public boolean isEmailExist(String email);

    public Customer changeCustomerValidateEmail(Customer SignUpCustomer);

    public Customer update(UpdateCustomerForm updateCustomerForm);

    public void customerVerify(String email);
}
