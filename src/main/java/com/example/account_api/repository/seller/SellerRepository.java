package com.example.account_api.repository.seller;

import com.example.account_api.domain.model.Customer;
import com.example.account_api.domain.model.Seller;

import java.util.Optional;

public interface SellerRepository extends SellerJPARepository {

    Seller save(Seller customer);
    Optional<Seller> findByEmail(String email);
}
