package com.example.order_api.repository;

import com.example.order_api.domain.model.Product;
import com.querydsl.jpa.impl.JPAQueryFactory;

import java.util.List;

public interface ProductRepositoryCustom {
    List<Product> searchByName(String name);
}
