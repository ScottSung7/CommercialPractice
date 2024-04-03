package com.example.accountapi.application.applications.signUp;

import com.example.accountapi.domain.model.Customer;
import com.example.accountapi.domain.model.Seller;
import com.example.accountapi.web.validation.form.customer.CustomerSignUpForm;
import com.example.accountapi.web.validation.form.customer.CustomerUpdateForm;
import com.example.accountapi.web.validation.form.seller.SellerSignUpForm;
import com.example.accountapi.web.validation.form.seller.SellerUpdateForm;


public interface SignUpApplication {
    public Customer customerSignUp(CustomerSignUpForm customerSignUpForm);

    public Customer customerUpdate(CustomerUpdateForm customerUpdateForm);

    public Seller sellerSignUp(SellerSignUpForm sellerSignUpForm);

    public Seller sellerUpdate(SellerUpdateForm sellerUpdateForm);

    public void customerVerify(String email);

    public void sellerVerify(String email);
}

