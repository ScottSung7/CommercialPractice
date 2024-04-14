package com.example.accountapi.application.applications.AccountInfo;

import com.example.accountapi.domain.model.Customer;
import com.example.accountapi.domain.model.Seller;

public interface AccountInfoApplication {
    public Customer findCustomer(String email);
    public Seller findSeller(String email);
}
