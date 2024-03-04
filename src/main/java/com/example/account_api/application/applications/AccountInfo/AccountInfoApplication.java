package com.example.account_api.application.applications.AccountInfo;

import com.example.account_api.domain.model.Customer;
import com.example.account_api.domain.model.Seller;

public interface AccountInfoApplication {

    public Customer findCustomer(String email);

    public Seller findSeller(String email);
}
