package com.example.orderapi.application.service;

import com.example.orderapi.domain.model.Product;
import com.example.orderapi.repository.ProductRepository;
import com.example.orderapi.web.validation.exception.OrderErrorCode;
import com.example.orderapi.web.validation.exception.OrderException;
import com.example.orderapi.web.validation.form.product.AddProductForm;
import com.example.orderapi.web.validation.form.product.UpdateProductForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{
    public final ProductRepository productRepository;

    @Transactional
    public Product addProduct(Long sellerId, AddProductForm form){
        productRepository.findByNameAndSellerId(form.getName(), sellerId)
                .ifPresent(product -> {
                    throw new OrderException(OrderErrorCode.SAME_PRODUCT_NAME);
                });
        return productRepository.save(Product.of(sellerId, form));
    }

    @Transactional
    public Product updateProduct(Long sellerId, UpdateProductForm form){
        Product product = productRepository.findBySellerIdAndId(sellerId, form.getId())
                .orElseThrow(() -> new OrderException(OrderErrorCode.NOT_FOUND_PRODUCT));

        return product.updateProduct(form, product);
    }

    @Transactional
    public void deleteProduct(Long sellerId, Long productId){
        Product product = productRepository.findBySellerIdAndId(sellerId, productId)
                .orElseThrow(() -> new OrderException(OrderErrorCode.NOT_FOUND_PRODUCT));
        productRepository.delete(product);
    }

    public List<Product> getMyProducts(Long sellerId) {
        List<Product> productList = productRepository.findBySellerId(sellerId);
        if(productList.isEmpty()){
            throw new OrderException(OrderErrorCode.NO_PRODUCT);
        }
        return productList;
    }
}
