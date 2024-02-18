package com.example.account_api.repository.customer;

import com.example.account_api.domain.model.Customer;

import java.util.Optional;

public interface CustomerRepository extends CustomerJPARepository {

    Optional<Customer> save(Customer customer);

}