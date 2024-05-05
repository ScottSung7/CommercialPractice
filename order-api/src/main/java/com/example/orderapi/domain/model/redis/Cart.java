package com.example.orderapi.domain.model.redis;

import com.example.orderapi.domain.model.Product;
import com.example.orderapi.domain.model.ProductItem;
import com.example.orderapi.web.validation.form.cart.AddProductCartForm;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.redis.core.RedisHash;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RedisHash("cart")
public class Cart {
    @Id
    private Long customerId;
    private List<Product> products = new ArrayList<>();
    private List<String> messages = new ArrayList<>();


    //Business Logic
    public static Cart getEmptyCart(Long customerId){
        Cart cart = new Cart();
        cart.setCustomerId(customerId);
        return cart;
    }
    public void collectProducts(List<Product> products) {
        this.products = products;
    }
    public void addMessage(String message){

        messages.add(message);
    }
    public void collectMessages(List<String> messages) {
        this.setMessages(messages);
    }
    public boolean checkEmptyCart() {
        return products.size() == 0;
    }
}
