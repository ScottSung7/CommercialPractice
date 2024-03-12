package com.example.orderapi.web.controller;


import com.example.orderapi.application.application.CartApplication;
import com.example.orderapi.domain.model.redis.Cart;
import com.example.orderapi.web.validation.form.AddProductCartForm;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer/cart")
@RequiredArgsConstructor
public class CustomerCartController {

    private final CartApplication cartApplication;
    //private final Cart

    public ResponseEntity<Cart> addCart(@RequestBody AddProductCartForm form) {

        return ResponseEntity.ok(cartApplication.addCart(1L, form));
    }
}
