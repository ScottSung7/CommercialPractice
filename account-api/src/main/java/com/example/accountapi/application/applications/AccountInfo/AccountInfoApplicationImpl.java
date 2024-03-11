package com.example.accountapi.application.applications.AccountInfo;

import com.example.accountapi.application.service.accountInfo.customer.AccountInfoCustomerService;
import com.example.accountapi.application.service.accountInfo.seller.AccountInfoSellerService;
import com.example.accountapi.domain.model.Customer;
import com.example.accountapi.domain.model.Seller;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountInfoApplicationImpl implements AccountInfoApplication{

    private final AccountInfoCustomerService accountInfoCustomerService;
    private final AccountInfoSellerService accountInfoSellerService;

    @Override
    public Customer findCustomer(String email) {
        return accountInfoCustomerService.findCustomer(email);
    }

    @Override
    public Seller findSeller(String email) {
        return accountInfoSellerService.findSeller(email);
    }
}
