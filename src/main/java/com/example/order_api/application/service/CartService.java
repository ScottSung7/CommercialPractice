package com.example.order_api.application.service;

import com.example.account_api.domain.redis.Cart;
import com.example.order_api.application.client.RedisClient;
import com.example.order_api.web.validation.form.AddProductCartForm;
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
        return redisClient.get(customerId, Cart.class);
    }

    public Cart addCart(Long customerId, AddProductCartForm form){
        Cart cart = redisClient.get(customerId, Cart.class);
        if (cart == null) {
            cart = new Cart();
            cart.setCustomerId(customerId);
        }
        //이전에 같은 상품이 있는지 확인
        Optional<Cart.Product> productOptional = cart.getProducts().stream()
                .filter(product -> product.getId().equals(form.getId()))
                .findFirst();

        //카트에 product있는지 확인
        if (productOptional.isPresent()) {//있다면 수량 추가
            Cart.Product redisProduct = productOptional.get();
            //req
            List<Cart.ProductItem> items = form.getItems().stream()
                    .map(Cart.ProductItem::from).collect(Collectors.toList());
            Map<Long, Cart.ProductItem> redisItemMap = redisProduct.getItems().stream()
                    .collect(Collectors.toMap(it -> it.getId(), it -> it));
            //Map사용하여 검색 속도 증진.
            if (!redisProduct.getName().equals(form.getName())) {
                cart.addMessage(redisProduct.getName()+"의 정보가 변경되었습니다. 확인 부탁드립니다.");
            }

            for (Cart.ProductItem item : items) {
                Cart.ProductItem redisItem = redisItemMap.get(item.getId());

                if (redisItem == null) {//없는 상품이면 추가
                    redisProduct.getItems().add(item);
                }else{//있으면 수량 추가
                    if(redisItem.getPrice().equals(item.getPrice())){
                        cart.addMessage(redisProduct.getName() + item.getName() +  "의 정보가 변경되었습니다. 확인 부탁 드립니다.");
                    }
                    redisItem.setCount(redisItem.getCount() + item.getCount());
                }
            }
        }else{//Product없으면 product 추가
            Cart.Product product = Cart.Product.from(form);
            cart.getProducts().add(product);
        }
        redisClient.put(customerId, cart);
        return cart;
    }

}
