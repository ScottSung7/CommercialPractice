package com.example.accountapi.repository.seller;

import com.example.accountapi.domain.model.Seller;

import java.util.Optional;

public interface SellerRepository extends SellerJPARepository {

    Seller save(Seller customer);
    Optional<Seller> findByEmail(String email);
}
