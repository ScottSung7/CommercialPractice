package com.example.orderapi.application.service;

import com.example.orderapi.domain.model.Product;

import java.util.List;

public interface ProductSearchService {

    public Product getByProductId(Long productId);

    public List<Product> getListByProductIds(List<Long> productIds);

    public List<Product> searchByName(String name);
}
