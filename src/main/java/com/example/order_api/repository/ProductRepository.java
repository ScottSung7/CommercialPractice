package com.example.order_api.repository;

import com.example.order_api.domain.model.Product;
import com.example.order_api.domain.model.ProductItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
