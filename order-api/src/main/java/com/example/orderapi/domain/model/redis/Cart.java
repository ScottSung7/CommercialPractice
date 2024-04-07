package com.example.orderapi.domain.model.redis;

import com.example.orderapi.domain.model.Product;
import com.example.orderapi.domain.model.ProductItem;
import com.example.orderapi.web.validation.form.cart.AddProductCartForm;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@RedisHash("cart")
public class Cart {
    @Id
    private Long customerId;
    private List<Product> products = new ArrayList<>();
    private List<String> messages = new ArrayList<>();

    public void addMessage(String message){
        messages.add(message);
    }


}
