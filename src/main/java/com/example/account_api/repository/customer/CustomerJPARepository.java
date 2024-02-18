package com.example.account_api.repository.customer;

import com.example.account_api.domain.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerJPARepository extends JpaRepository<Customer, Long> {
}
