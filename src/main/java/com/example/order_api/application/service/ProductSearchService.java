package com.example.order_api.application.service;

import com.example.order_api.domain.model.Product;
import com.example.order_api.repository.ProductRepository;
import com.example.order_api.web.validation.exception.OrderErrorCode;
import com.example.order_api.web.validation.exception.OrderException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductSearchService {

    private final ProductRepository productRepository;


    public Product getByProductId(Long productId){
        return productRepository.findWithProductItemById(productId)
            .orElseThrow(() -> new OrderException(OrderErrorCode.NOT_FOUND_PRODUCT));
    }
    public List<Product> getListByProductIds(List<Long> productIds){
        return productRepository.findAllById(productIds);
    }

    public List<Product> searchByName(String name) {
        return productRepository.searchByName(name);
    }
}
