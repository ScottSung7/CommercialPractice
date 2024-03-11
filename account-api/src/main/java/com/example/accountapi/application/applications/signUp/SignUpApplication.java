package com.example.accountapi.application.applications.signUp;

import com.example.accountapi.domain.model.Customer;
import com.example.accountapi.web.validation.form.customer.SignUpCustomerForm;
import com.example.accountapi.web.validation.form.customer.UpdateCustomerForm;
import com.example.accountapi.web.validation.form.seller.SignUpSellerForm;


public interface SignUpApplication {
    public String customerSignUp(SignUpCustomerForm signUpCustomerForm);

    public Customer customerUpdate(UpdateCustomerForm updateCustomerForm);

    public String sellerSignUp(SignUpSellerForm signUpSellerForm);

    public void customerVerify(String email);

    public void sellerVerify(String email);
}

