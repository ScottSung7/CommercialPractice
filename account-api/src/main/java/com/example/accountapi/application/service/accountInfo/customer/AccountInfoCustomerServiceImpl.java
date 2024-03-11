package com.example.accountapi.application.service.accountInfo.customer;

import com.example.accountapi.domain.model.Customer;
import com.example.accountapi.repository.customer.CustomerRepository;
import com.example.accountapi.web.validation.exception.AccountException;
import com.example.accountapi.web.validation.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountInfoCustomerServiceImpl implements AccountInfoCustomerService{

    private final CustomerRepository customerRepository;
    @Override
    public Customer findCustomer(String email) {
        return customerRepository.findByEmail(email).orElseThrow(
                () -> new AccountException(ErrorCode.NOT_FOUND_USER)
        );
    }
}
