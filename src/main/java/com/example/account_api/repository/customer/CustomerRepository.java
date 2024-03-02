package com.example.account_api.repository.customer;

import com.example.account_api.domain.model.Customer;

import java.util.Optional;

public interface CustomerRepository extends CustomerJPARepository {

    Customer save(Customer customer);
    Optional<Customer> findByEmail(String email);

    Optional<Integer> deleteByEmail(String email);
}
