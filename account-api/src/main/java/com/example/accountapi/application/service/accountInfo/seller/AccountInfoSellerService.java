package com.example.accountapi.application.service.accountInfo.seller;

import com.example.accountapi.domain.model.Seller;

public interface AccountInfoSellerService {
    public Seller findSeller(String email);
}
