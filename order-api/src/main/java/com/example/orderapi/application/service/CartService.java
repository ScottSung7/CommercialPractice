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
        if (cart == null) {
            cart = new Cart();
            cart.setCustomerId(customerId);
        }
        //이전에 같은 상품이 있는지 확인
        Optional<Product> productOptional = cart.getProducts().stream()
                .filter(product -> product.getId().equals(form.getId()))
                .findFirst();

        //카트에 product있는지 확인
        if (productOptional.isPresent()) {//있다면 수량 추가
            Product redisProduct = productOptional.get();
            //req
            List<ProductItem> items = form.getAddCartProductItemForms().stream()
                    .map(ProductItem::from).collect(Collectors.toList());
            Map<Long, ProductItem> redisItemMap = redisProduct.getProductItems().stream()
                    .collect(Collectors.toMap(it -> it.getId(), it -> it));
            //Map사용하여 검색 속도 증진.
            if (!redisProduct.getName().equals(form.getName())) {
                cart.addMessage(redisProduct.getName()+"의 정보가 변경되었습니다. 확인 부탁드립니다.");
            }

            for (ProductItem item : items) {
                ProductItem redisItem = redisItemMap.get(item.getId());

                if (redisItem == null) {//없는 상품이면 추가
                    redisProduct.getProductItems().add(item);
                }else{//있으면 수량 추가
                    if(!redisItem.getPrice().equals(item.getPrice())){
                        cart.addMessage(redisProduct.getName() + item.getName() +  "의 정보가 변경되었습니다. 확인 부탁 드립니다.");
                    }
                    redisItem.setCount(redisItem.getCount() + item.getCount());
                }
            }
        }else{//Product없으면 product 추가
            Product product = Product.from(form);
            cart.getProducts().add(product);
        }
        redisClient.put(customerId, cart);
        return cart;
    }

}
