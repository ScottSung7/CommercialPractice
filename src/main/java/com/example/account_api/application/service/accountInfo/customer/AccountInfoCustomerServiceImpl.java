package com.example.account_api.application.service.accountInfo.customer;

import com.example.account_api.domain.model.Customer;
import com.example.account_api.repository.customer.CustomerRepository;
import com.example.account_api.web.validation.exception.AccountException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.example.account_api.web.validation.exception.ErrorCode.NOT_FOUND_USER;

@Service
@RequiredArgsConstructor
public class AccountInfoCustomerServiceImpl implements AccountInfoCustomerService{

    private final CustomerRepository customerRepository;
    @Override
    public Customer findCustomer(String email) {
        return customerRepository.findByEmail(email).orElseThrow(
                () -> new AccountException(NOT_FOUND_USER)
        );
    }
}
