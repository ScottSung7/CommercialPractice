package com.example.accountapi.application.service.signUp.customer;

import com.example.accountapi.domain.model.Customer;
import com.example.accountapi.web.validation.form.customer.CustomerSignUpForm;
import com.example.accountapi.web.validation.form.customer.CustomerUpdateForm;

public interface SignUpCustomerService {

    public Customer signUp(CustomerSignUpForm form);

    public boolean isEmailExist(String email);

    public Customer addExpirationDate(Customer SignUpCustomer, int num);

    public Customer update(CustomerUpdateForm customerUpdateForm);

    public boolean customerVerify(String email);

}
