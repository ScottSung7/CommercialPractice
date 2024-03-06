package com.example.order_api.application.service;

import com.example.order_api.domain.model.Product;
import com.example.order_api.domain.model.ProductItem;
import com.example.order_api.domain.model.dto.ProductItemDto;
import com.example.order_api.repository.ProductRepository;
import com.example.order_api.web.validation.exception.OrderErrorCode;
import com.example.order_api.web.validation.exception.OrderException;
import com.example.order_api.web.validation.form.AddProductForm;
import com.example.order_api.web.validation.form.UpdateProductForm;
import com.example.order_api.web.validation.form.UpdateProductItemForm;
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

    @Transactional
    public Product updateProduct(Long sellerId, UpdateProductForm form){
        Product product = productRepository.findBySellerIdAndId(sellerId, form.getId())
                .orElseThrow(() -> new OrderException(OrderErrorCode.NOT_FOUND_PRODUCT));
        product.setName(form.getName());
        product.setDescription(form.getDescription());

        for(UpdateProductItemForm itemForm : form.getItems()){
            ProductItem item = product.getProductItems().stream()
                    .filter(pi -> pi.getId().equals(itemForm.getId()))
                    .findFirst().orElseThrow(() -> new OrderException(OrderErrorCode.NOT_FOUND_ITEM));
            item.setName(itemForm.getName());
            item.setPrice(itemForm.getPrice());
            item.setCount(item.getCount());
        }
        return product;
    }

    public void deleteProduct(Long sellerId, Long productId){
        Product product = productRepository.findBySellerIdAndId(sellerId, productId)
                .orElseThrow(() -> new OrderException(OrderErrorCode.NOT_FOUND_PRODUCT));

        productRepository.delete(product);

    }

}
