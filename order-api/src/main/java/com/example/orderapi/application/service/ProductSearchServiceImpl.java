package com.example.orderapi.application.service;

import com.example.orderapi.domain.model.Product;
import com.example.orderapi.repository.ProductRepository;
import com.example.orderapi.web.validation.exception.OrderErrorCode;
import com.example.orderapi.web.validation.exception.OrderException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductSearchServiceImpl implements ProductSearchService{

    private final ProductRepository productRepository;

    public Product getByProductId(Long productId){
        return productRepository.findWithProductItemById(productId)
            .orElseThrow(() -> new OrderException(OrderErrorCode.NOT_FOUND_PRODUCT));
    }
    public List<Product> getListByProductIds(List<Long> productIds){
        List<Product> productList = productRepository.findAllById(productIds);
        if(productList.isEmpty()){
            throw new OrderException(OrderErrorCode.NOT_FOUND_PRODUCT);
        }
        return productList;
    }

    public List<Product> searchByName(String name) {
        List<Product> productList = productRepository.searchByName(name);
        if(productList.isEmpty()){
            throw new OrderException(OrderErrorCode.NOT_FOUND_PRODUCT);
        }
        return productList;
    }
}
