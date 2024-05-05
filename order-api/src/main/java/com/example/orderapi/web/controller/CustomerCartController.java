package com.example.orderapi.web.controller;


import com.example.orderapi.application.application.CartApplication;
import com.example.orderapi.config.common.SpringSecurity.id.UserPrincipalDetails;
import com.example.orderapi.config.common.validation.LoginCheckMSA;
import com.example.orderapi.domain.model.redis.Cart;
import com.example.orderapi.web.validation.exception.OrderErrorCode;
import com.example.orderapi.web.validation.exception.OrderException;
import com.example.orderapi.web.validation.form.cart.AddProductCartForm;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer/cart")
@RequiredArgsConstructor
public class CustomerCartController {

     private final CartApplication cartApplication;

    @PostMapping
    @Schema(name="AddProductCartForm", implementation = AddProductCartForm.class)
    public ResponseEntity<Cart> addCart(@RequestBody AddProductCartForm form, Authentication authentication) {
        UserPrincipalDetails customer = LoginCheckMSA.customerCheck(authentication);

        return ResponseEntity.ok(cartApplication.addCart(customer.getId(), form));
    }

    @GetMapping
    public ResponseEntity<Cart> showCart(Authentication authentication) {
        UserPrincipalDetails customer = LoginCheckMSA.customerCheck(authentication);
        return ResponseEntity.ok(cartApplication.getCart(customer.getId()));
    }
    @DeleteMapping
    public ResponseEntity<String> clearCart(Authentication authentication) {
        UserPrincipalDetails customer = LoginCheckMSA.customerCheck(authentication);
        cartApplication.clearCart(customer.getId());
        return ResponseEntity.ok("장바구니가 비워졌습니다.");
    }


}
