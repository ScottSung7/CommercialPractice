package com.example.account_api.application.service.accountInfo.seller;

import com.example.account_api.domain.model.Customer;
import com.example.account_api.domain.model.Seller;

public interface AccountInfoSellerService {
    public Seller findSeller(String email);
}
