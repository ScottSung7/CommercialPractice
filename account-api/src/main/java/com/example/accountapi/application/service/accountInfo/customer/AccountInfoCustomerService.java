package com.example.accountapi.application.service.accountInfo.customer;

import com.example.accountapi.domain.model.Customer;

public interface AccountInfoCustomerService {
    public Customer findCustomer(String email);
}
