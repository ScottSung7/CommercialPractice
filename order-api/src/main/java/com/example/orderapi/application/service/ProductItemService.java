package com.example.orderapi.application.service;

import com.example.orderapi.domain.model.Product;
import com.example.orderapi.domain.model.ProductItem;
import com.example.orderapi.repository.ProductItemRepository;
import com.example.orderapi.repository.ProductRepository;
import com.example.orderapi.web.validation.exception.OrderException;
import com.example.orderapi.web.validation.form.productItem.AddExtraProductItemForm;
import com.example.orderapi.web.validation.exception.OrderErrorCode;
import com.example.orderapi.web.validation.form.productItem.UpdateProductItemForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductItemService {
    private final ProductRepository productRepository;
    private final ProductItemRepository productItemRepository;

    @Transactional
    public Product addProductItem(Long sellerId, AddExtraProductItemForm form){
        Product product = productRepository.findBySellerIdAndId(sellerId, form.getProductId())
                .orElseThrow(() -> new OrderException(OrderErrorCode.NOT_FOUND_PRODUCT));
        product.getProductItems().stream()
                .filter(item -> item.getName().equals(form.getName()))
                .findAny()
                .ifPresent(item -> {
                    throw new OrderException(OrderErrorCode.SAME_ITEM_NAME);
                });

        product.addProductItem(product, sellerId, form);

        return product;
    }



    @Transactional
    public ProductItem  updateProductItem(Long sellerId, UpdateProductItemForm form){
        ProductItem productItem = productItemRepository.findById(form.getItemId())
                .filter(pi -> pi.getSellerId().equals(sellerId)).orElseThrow(
                        () -> new OrderException(OrderErrorCode.NOT_FOUND_ITEM)
                );
        return productItem.updateProductItem(form);
    }
    @Transactional
    public void deleteProductItem(Long sellerId, Long productItemId){
        System.out.println("deleting?");
        ProductItem productItem = productItemRepository.findById(productItemId)
                .filter(pi -> pi.getSellerId().equals(sellerId))
                .orElseThrow(()  -> new OrderException(OrderErrorCode.NOT_FOUND_ITEM));
        productItemRepository.delete(productItem);

    }

}
