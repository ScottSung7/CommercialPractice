package com.example.orderapi.application.service;

import com.example.orderapi.application.client.RedisClient;
import com.example.orderapi.domain.model.Product;
import com.example.orderapi.domain.model.redis.Cart;
import com.example.orderapi.web.validation.form.cart.AddCartProductItemForm;
import com.example.orderapi.web.validation.form.cart.AddProductCartForm;
import com.example.orderapi.web.validation.form.product.AddProductForm;
import com.example.orderapi.web.validation.form.productItem.AddProductItemForm;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@Transactional
class CartServiceTest {


    @Autowired
    CartService cartService;

    @AfterEach
    public void after(){
        cartService.putCart(1L, null);
    }

    @Test
    void getCart() {
        //given - add Cart
        Long customerId = 1L;
        AddProductCartForm addProductCartForm = mock(AddProductCartForm.class);
        AddCartProductItemForm addCartProductItemForm = mock(AddCartProductItemForm.class);

        given(addProductCartForm.getId()).willReturn(1L);
        given(addProductCartForm.getName()).willReturn("testProduct");
        given(addProductCartForm.getDescription()).willReturn("testDescription");
        given(addProductCartForm.getAddCartProductItemForms()).willReturn(List.of(addCartProductItemForm));
        given(addCartProductItemForm.getProductId()).willReturn(1L);
        given(addCartProductItemForm.getName()).willReturn("testProductItem");
        given(addCartProductItemForm.getPrice()).willReturn(1000);
        given(addCartProductItemForm.getCount()).willReturn(1);

        Cart cartAdded = cartService.addCart(customerId, addProductCartForm);

        //when
        Cart cartReturned = cartService.getCart(customerId);

        //then
        assertEquals(customerId, cartReturned.getCustomerId());
        assertEquals(addProductCartForm.getId(), cartReturned.getProducts().get(0).getId());
        assertEquals(addProductCartForm.getName(), cartReturned.getProducts().get(0).getName());
        assertEquals(addProductCartForm.getDescription(), cartReturned.getProducts().get(0).getDescription());
        assertEquals(addProductCartForm.getAddCartProductItemForms().get(0).getProductId(), cartReturned.getProducts().get(0).getProductItems().get(0).getId());
        assertEquals(addProductCartForm.getAddCartProductItemForms().get(0).getName(), cartAdded.getProducts().get(0).getProductItems().get(0).getName());
        assertEquals(addProductCartForm.getAddCartProductItemForms().get(0).getPrice(), cartAdded.getProducts().get(0).getProductItems().get(0).getPrice());
        assertEquals(1, cartAdded.getProducts().get(0).getProductItems().get(0).getCount());
    }

    @Test
    void putCart() {
        Long customerId = 1L;
        //given
        Cart cart = mock(Cart.class);
        given(cart.getCustomerId()).willReturn(customerId);

        //when
        cartService.putCart(customerId, cart);

        //then
        Cart cartReturned = cartService.getCart(customerId);

        assertEquals(customerId, cartReturned.getCustomerId());

    }
    @Test
    void addCart() {

        //given - add Cart
        Long customerId = 1L;
        AddProductCartForm addProductCartForm = mock(AddProductCartForm.class);
        AddCartProductItemForm addCartProductItemForm = mock(AddCartProductItemForm.class);

        given(addProductCartForm.getId()).willReturn(1L);
        given(addProductCartForm.getName()).willReturn("testProduct");
        given(addProductCartForm.getDescription()).willReturn("testDescription");
        given(addProductCartForm.getAddCartProductItemForms()).willReturn(List.of(addCartProductItemForm));
        given(addCartProductItemForm.getProductId()).willReturn(1L);
        given(addCartProductItemForm.getName()).willReturn("testProductItem");
        given(addCartProductItemForm.getPrice()).willReturn(1000);
        given(addCartProductItemForm.getCount()).willReturn(1);

        //when
        Cart cartAdded = cartService.addCart(customerId, addProductCartForm);

        assertEquals(customerId, cartAdded.getCustomerId());
        assertEquals(addProductCartForm.getId(), cartAdded.getProducts().get(0).getId());
        assertEquals(addProductCartForm.getName(), cartAdded.getProducts().get(0).getName());
        assertEquals(addProductCartForm.getDescription(), cartAdded.getProducts().get(0).getDescription());
        assertEquals(addProductCartForm.getAddCartProductItemForms().get(0).getProductId(), cartAdded.getProducts().get(0).getProductItems().get(0).getId());
        assertEquals(addProductCartForm.getAddCartProductItemForms().get(0).getName(), cartAdded.getProducts().get(0).getProductItems().get(0).getName());
        assertEquals(addProductCartForm.getAddCartProductItemForms().get(0).getPrice(), cartAdded.getProducts().get(0).getProductItems().get(0).getPrice());
        assertEquals(1, cartAdded.getProducts().get(0).getProductItems().get(0).getCount());


    }
    @Test
    void checkProductNameChanged() {

        //given - add Cart
        Long customerId = 1L;
        AddProductCartForm addProductCartForm = mock(AddProductCartForm.class);
        AddCartProductItemForm addCartProductItemForm = mock(AddCartProductItemForm.class);

        given(addProductCartForm.getId()).willReturn(1L);
        given(addProductCartForm.getName()).willReturn("testProduct");
        given(addProductCartForm.getDescription()).willReturn("testDescription");
        given(addProductCartForm.getAddCartProductItemForms()).willReturn(List.of(addCartProductItemForm));
        given(addCartProductItemForm.getProductId()).willReturn(1L);
        given(addCartProductItemForm.getName()).willReturn("testProductItem");
        given(addCartProductItemForm.getPrice()).willReturn(1000);
        given(addCartProductItemForm.getCount()).willReturn(1);
        cartService.addCart(customerId, addProductCartForm);

        // When
        given(addProductCartForm.getName()).willReturn("NewProductName");
        Cart cart = cartService.addCart(customerId, addProductCartForm);

        // Then
        assertEquals(1, cart.getMessages().size());
        assertEquals("testProduct의 상품명이 변경되었습니다. 확인 부탁드립니다.", cart.getMessages().get(0));
    }
    @Test
    void itemPriceChanged() {

        //given - add Cart
        Long customerId = 1L;
        AddProductCartForm addProductCartForm = mock(AddProductCartForm.class);
        AddCartProductItemForm addCartProductItemForm = mock(AddCartProductItemForm.class);

        given(addProductCartForm.getId()).willReturn(1L);
        given(addProductCartForm.getName()).willReturn("testProduct");
        given(addProductCartForm.getDescription()).willReturn("testDescription");
        given(addProductCartForm.getAddCartProductItemForms()).willReturn(List.of(addCartProductItemForm));
        given(addCartProductItemForm.getProductId()).willReturn(1L);
        given(addCartProductItemForm.getName()).willReturn("testProductItem");
        given(addCartProductItemForm.getPrice()).willReturn(1000);
        given(addCartProductItemForm.getCount()).willReturn(1);
        cartService.addCart(customerId, addProductCartForm);

        // When
        given(addCartProductItemForm.getPrice()).willReturn(200);
        given(addProductCartForm.getAddCartProductItemForms()).willReturn(List.of(addCartProductItemForm));

        Cart cart = cartService.addCart(customerId, addProductCartForm);

        // Then
        assertEquals(1, cart.getMessages().size());
        assertEquals("testProduct에서 testProductItem의 가격이 변경되었습니다. 확인 부탁 드립니다.", cart.getMessages().get(0));
    }
    @Test
    void itemNameChanged() {

        //given - add Cart
        Long customerId = 1L;
        AddProductCartForm addProductCartForm = mock(AddProductCartForm.class);
        AddCartProductItemForm addCartProductItemForm = mock(AddCartProductItemForm.class);

        given(addProductCartForm.getId()).willReturn(1L);
        given(addProductCartForm.getName()).willReturn("testProduct");
        given(addProductCartForm.getAddCartProductItemForms()).willReturn(List.of(addCartProductItemForm));
        given(addCartProductItemForm.getProductId()).willReturn(1L);
        given(addCartProductItemForm.getName()).willReturn("testProductItem");
        given(addCartProductItemForm.getPrice()).willReturn(1000);
        given(addCartProductItemForm.getCount()).willReturn(1);
        cartService.addCart(customerId, addProductCartForm);

        // When
        given(addCartProductItemForm.getName()).willReturn("newName");
        given(addProductCartForm.getAddCartProductItemForms()).willReturn(List.of(addCartProductItemForm));

        Cart cart = cartService.addCart(customerId, addProductCartForm);

        // Then
        assertEquals(1, cart.getMessages().size());
        assertEquals("testProduct에서 testProductItem의 아이템명이 변경되었습니다. 확인 부탁 드립니다.", cart.getMessages().get(0));
    }

}