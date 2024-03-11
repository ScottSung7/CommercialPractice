package com.example.accountapi.repository.customer;

import com.example.accountapi.domain.model.Customer;

import java.util.Optional;

public interface CustomerRepository extends CustomerJPARepository {

    Customer save(Customer customer);
    Optional<Customer> findByEmail(String email);

    Optional<Integer> deleteByEmail(String email);
}
