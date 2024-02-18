package com.example.account_api.service;

import com.example.account_api.domain.model.Customer;
import com.example.account_api.repository.customer.CustomerRepository;
import com.example.account_api.web.validation.form.SignUpCustomerForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SignUpCustomerServiceImpl implements SignUpCustomerService{

    private final CustomerRepository customerRepository;

    public Optional<Customer> signUp(SignUpCustomerForm form){
        return customerRepository.save(Customer.from(form));
    }

}
