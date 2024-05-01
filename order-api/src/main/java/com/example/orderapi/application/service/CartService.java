package com.example.orderapi.application.service;

import com.example.orderapi.application.client.RedisClient;
import com.example.orderapi.domain.model.Product;
import com.example.orderapi.domain.model.ProductItem;
import com.example.orderapi.domain.model.redis.Cart;
import com.example.orderapi.web.validation.form.cart.AddProductCartForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class CartService {
    private final RedisClient redisClient;

    public Cart getCart(Long customerId){
    Cart cart = redisClient.get(customerId,Cart.class);
        return cart!=null?cart:new Cart();
    }
    public Cart putCart(Long customerId,Cart cart){
        redisClient.put(customerId,cart);
        return cart;
    }

    public Cart addCart(Long customerId, AddProductCartForm form){
        Cart cart = redisClient.get(customerId, Cart.class);
        checkCartIsNull(cart, customerId);

        Optional<Product> productOptional = checkProductAlreadyInCart(cart, form);
        addProductToCart(cart, productOptional, form);

        redisClient.put(customerId, cart);
        return cart;
    }
    private void checkCartIsNull(Cart cart, Long customerId) {
        if (cart == null) {
            cart = new Cart();
            cart.setCustomerId(customerId);
        }
    }
    private Optional<Product> checkProductAlreadyInCart(Cart cart, AddProductCartForm form) {
        return cart.getProducts().stream()
                .filter(product -> product.getId().equals(form.getId()))
                .findFirst();
    }
    private void addProductToCart(Cart cart, Optional<Product> productOptional, AddProductCartForm form) {
        if (productOptional.isPresent()) {

            Product redisProduct = productOptional.get();
            List<ProductItem> items = form.getAddCartProductItemForms().stream()
                    .map(ProductItem::from).collect(Collectors.toList());
            Map<Long, ProductItem> redisItemMap = redisProduct.getProductItems().stream()
                    .collect(Collectors.toMap(it -> it.getId(), it -> it));

            checkProductNameChanged(cart, redisProduct, form);
            manageProductItemPriceAndCount(cart, redisProduct, items, redisItemMap);
        }else{
            Product product = Product.from(form);
            cart.getProducts().add(product);
        }
    }

    private void checkProductNameChanged(Cart cart, Product redisProduct, AddProductCartForm form) {
        if (!redisProduct.getName().equals(form.getName())) {
            cart.addMessage(redisProduct.getName()+"의 정보가 변경되었습니다. 확인 부탁드립니다.");
        }
    }
    private void manageProductItemPriceAndCount(Cart cart, Product redisProduct, List<ProductItem> items, Map<Long, ProductItem> redisItemMap) {
        for (ProductItem item : items) {
            ProductItem redisItem = redisItemMap.get(item.getId());
            if (redisItem == null) {
                redisProduct.getProductItems().add(item);
            }else{
                if(!redisItem.getPrice().equals(item.getPrice())){
                    cart.addMessage(redisProduct.getName() + item.getName() +  "의 정보가 변경되었습니다. 확인 부탁 드립니다.");
                }
                int changedCount = item.getCount() - redisItem.getCount();
                redisItem.changeCount(changedCount);
            }
        }
    }


}
