package com.example.accountapi.repository.seller;

import com.example.accountapi.domain.model.Seller;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SellerJPARepository extends JpaRepository<Seller, Long> {

    Optional<Seller> findByEmail(String email);
}
