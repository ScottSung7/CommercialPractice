package com.example.order_api.application.service;

import com.example.order_api.domain.model.Product;
import com.example.order_api.repository.ProductRepository;
import com.example.order_api.web.validation.form.AddProductForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductService {
    public final ProductRepository productRepository;

    @Transactional
    public Product addProduct(Long sellerId, AddProductForm form){
        return productRepository.save(Product.of(sellerId, form));
    }

}
