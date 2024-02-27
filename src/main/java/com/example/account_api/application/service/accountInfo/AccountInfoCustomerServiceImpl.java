package com.example.account_api.application.service.accountInfo;

import com.example.account_api.domain.model.Customer;
import com.example.account_api.repository.customer.CustomerRepository;
import com.example.account_api.web.validation.exception.AccountException;
import com.example.account_api.web.validation.exception.ErrorCode;
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
