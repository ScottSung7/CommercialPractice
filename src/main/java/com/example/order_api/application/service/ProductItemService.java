package com.example.order_api.application.service;

import com.example.order_api.domain.model.Product;
import com.example.order_api.domain.model.ProductItem;
import com.example.order_api.repository.ProductItemRepository;
import com.example.order_api.repository.ProductRepository;
import com.example.order_api.web.validation.exception.OrderException;
import com.example.order_api.web.validation.form.AddProductItemForm;
import com.example.order_api.web.validation.form.UpdateProductItemForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.order_api.web.validation.exception.OrderErrorCode.*;

@Service
@RequiredArgsConstructor
public class ProductItemService {
    private final ProductRepository productRepository;
    private final ProductItemRepository productItemRepository;

    @Transactional
    public Product addProductItem(Long sellerId, AddProductItemForm form){
        Product product = productRepository.findBySellerIdAndId(sellerId, form.getProductId())
                .orElseThrow(() -> new OrderException(NOT_FOUND_PRODUCT));

        if(product.getProductItems().stream()
                .anyMatch(item -> item.getName().equals(form.getName()))){
            throw new OrderException(SAME_ITEM_NAME);
        }

        ProductItem productItem = ProductItem.of(sellerId, form);
        product.getProductItems().add(productItem);
        return product;
    }

    @Transactional
    public ProductItem updateProductItem(Long sellerId, UpdateProductItemForm form){
        ProductItem productItem = productItemRepository.findById(form.getId())
                .filter(pi -> !pi.getSellerId().equals(sellerId)).orElseThrow(
                        () -> new OrderException(NOT_FOUND_ITEM)
                );
        productItem.setName(form.getName());
        productItem.setCount(form.getCount());
        productItem.setPrice(form.getPrice());
        return productItem;
    }
    @Transactional
    public void deleteProductItem(Long sellerId, Long productItemId){
        ProductItem productItem = productItemRepository.findById(productItemId)
                .filter(pi -> pi.getSellerId().equals(sellerId))
                .orElseThrow(()  -> new OrderException(NOT_FOUND_ITEM));
        productItemRepository.delete(productItem);
    }

}
