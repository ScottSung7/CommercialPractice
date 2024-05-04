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
        Cart cart = redisClient.get(customerId, Cart.class);
        if (cart == null) {
            return Cart.getEmptyCart(customerId);
        }else{
            return cart;
        }
    }
    public void putCart(Long customerId,Cart cart){
        redisClient.put(customerId,cart);
    }

    public Cart addCart(Long customerId, AddProductCartForm form){
        Cart cart = redisClient.get(customerId, Cart.class);
        cart = checkCartIsNull(cart, customerId);

        Optional<Product> productOptional = checkProductAlreadyInCart(cart, form);
        addProductToCart(cart, productOptional, form);
        redisClient.put(customerId, cart);
        return cart;
    }

    //Private Methods for addCart
    private Cart checkCartIsNull(Cart cart, Long customerId) {
        if (cart == null) {
            cart = Cart.getEmptyCart(customerId);
        }
        return cart;
    }
    private Optional<Product> checkProductAlreadyInCart(Cart cart, AddProductCartForm form) {
        return cart.getProducts().stream()
                .filter(product -> product.getId().equals(form.getId()))
                .findFirst();
    }
    private void addProductToCart(Cart cart, Optional<Product> productOptional, AddProductCartForm form) {
        if (productOptional.isPresent()) {
            Product redisProduct = productOptional.get();
            checkProductNameChanged(cart, redisProduct, form);
            manageProductItem(cart, redisProduct, form);
        }else{
            Product product = Product.from(form);
            cart.getProducts().add(product);
        }
    }
    private void manageProductItem(Cart cart, Product redisProduct, AddProductCartForm form) {
        List<ProductItem> itemListToAdd = form.getAddCartProductItemForms().stream()
                .map(ProductItem::from).collect(Collectors.toList());
        Map<Long, ProductItem> itemMapFromRedis = redisProduct.getProductItems().stream()
                .collect(Collectors.toMap(it -> it.getId(), it -> it));
        manageProductItemPriceAndCount(cart, redisProduct, itemListToAdd, itemMapFromRedis);
    }
    private void checkProductNameChanged(Cart cart, Product redisProduct, AddProductCartForm form) {
        if (!redisProduct.getName().equals(form.getName())) {
            cart.addMessage(redisProduct.getName()+"의 정보가 변경되었습니다. 확인 부탁드립니다.");
        }
    }
    private void manageProductItemPriceAndCount(Cart cart, Product redisProduct, List<ProductItem> itemListToAdd, Map<Long, ProductItem> itemMapFromRedis) {
        for (ProductItem item : itemListToAdd) {
            ProductItem redisItem = itemMapFromRedis.get(item.getId());
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
