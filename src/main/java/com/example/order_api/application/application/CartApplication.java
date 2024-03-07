package com.example.order_api.application.application;

import com.example.account_api.domain.redis.Cart;
import com.example.order_api.application.service.CartService;
import com.example.order_api.application.service.ProductSearchService;
import com.example.order_api.domain.model.Product;
import com.example.order_api.domain.model.ProductItem;
import com.example.order_api.web.validation.exception.OrderErrorCode;
import com.example.order_api.web.validation.exception.OrderException;
import com.example.order_api.web.validation.form.AddProductCartForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartApplication {
    private final ProductSearchService productSearchService;
    private final CartService cartService;

    public Cart addCart(Long customerId, AddProductCartForm form){

        Product product = productSearchService.getByProductId(form.getId());
        if (product == null) {
            throw new OrderException(OrderErrorCode.NOT_FOUND_PRODUCT);
        }
        Cart cart = cartService.getCart(customerId);

        if (cart != null && !addAble(cart, product, form)) {
            throw new OrderException(OrderErrorCode.NOT_FOUND_PRODUCT);
        }

       return cartService.addCart(customerId, form);
    }

    private boolean addAble(Cart cart,Product product, AddProductCartForm form) {
        Cart.Product cartProduct = cart.getProducts().stream().filter(p -> p.getId().equals(form.getId()))
                .findFirst().orElseThrow(() -> new OrderException(OrderErrorCode.NOT_FOUND_PRODUCT));

        //물품의 수량이 충분한지 확인
        Map<Long,Integer> cartItemCount = cartProduct.getItems().stream()
                .collect(Collectors.toMap(Cart.ProductItem::getId, Cart.ProductItem::getCount));
        Map<Long,Integer> currentItemCountMap = product.getProductItems().stream()
                .collect(Collectors.toMap(ProductItem::getId, ProductItem::getCount));


        return form.getItems().stream().noneMatch(
                formItem -> {
                    Integer cartCount = cartItemCount.get(formItem.getId());
                    Integer currentCount = currentItemCountMap.get(formItem.getId());
                    return formItem.getCount() + cartCount > currentCount;
                });
        
    }

}
