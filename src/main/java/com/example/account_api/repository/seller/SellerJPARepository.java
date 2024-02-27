package com.example.account_api.repository.seller;

import com.example.account_api.domain.model.Customer;
import com.example.account_api.domain.model.Seller;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SellerJPARepository extends JpaRepository<Seller, Long> {

    Optional<Seller> findByEmail(String email);
}
