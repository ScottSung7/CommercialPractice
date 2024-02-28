package com.example.account_api.application.service.accountInfo.customer;

import com.example.account_api.domain.model.Customer;

public interface AccountInfoCustomerService {
    public Customer findCustomer(String email);
}
