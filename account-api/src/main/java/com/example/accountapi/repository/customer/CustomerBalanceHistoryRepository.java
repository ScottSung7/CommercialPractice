package com.example.accountapi.repository.customer;

import com.example.accountapi.domain.model.CustomerBalanceHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerBalanceHistoryRepository extends JpaRepository<CustomerBalanceHistory, Long> {

    Optional<CustomerBalanceHistory> findFirstByCustomer_IdOrderByIdDesc(Long customerId);
}
