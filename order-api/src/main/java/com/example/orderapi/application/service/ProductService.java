package com.example.orderapi.application.service;

import com.example.orderapi.domain.model.Product;
import com.example.orderapi.web.validation.form.product.AddProductForm;
import com.example.orderapi.web.validation.form.product.UpdateProductForm;

import java.util.List;

public interface ProductService {

    public Product addProduct(Long sellerId, AddProductForm form);

    public Product updateProduct(Long sellerId, UpdateProductForm form);

    public void deleteProduct(Long sellerId, Long productId);

    public List<Product> getMyProducts(Long sellerId);
}
