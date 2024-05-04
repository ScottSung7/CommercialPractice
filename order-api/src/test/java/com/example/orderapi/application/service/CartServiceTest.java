package com.example.orderapi.application.service;

import com.example.orderapi.application.client.RedisClient;
import com.example.orderapi.domain.model.Product;
import com.example.orderapi.domain.model.redis.Cart;
import com.example.orderapi.web.validation.form.cart.AddCartProductItemForm;
import com.example.orderapi.web.validation.form.cart.AddProductCartForm;
import com.example.orderapi.web.validation.form.product.AddProductForm;
import com.example.orderapi.web.validation.form.productItem.AddProductItemForm;
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

    @Mock
    AddProductCartForm addProductCartForm;

    @Mock
    AddCartProductItemForm addProductCartItemForm;

    @Autowired
    CartService cartService;

    @Mock
    AddProductForm addProductForm;

    @Mock
    AddProductItemForm addProductItemForm;

    private Long sellerId;
    @BeforeEach
    public void before(){
        sellerId = 1L;

        addProductForm = mock(AddProductForm.class, withSettings().lenient());
        addProductItemForm = mock(AddProductItemForm.class, withSettings().lenient());

        given(addProductForm.getName()).willReturn("testProduct");
        given(addProductForm.getDescription()).willReturn("testDescription");

        given(addProductItemForm.getName()).willReturn("testProductItem");
        given(addProductItemForm.getPrice()).willReturn(1000);
        given(addProductItemForm.getCount()).willReturn(10);

        List<AddProductItemForm> itemForms = new ArrayList<>();
        itemForms.add(addProductItemForm);
        given(addProductForm.getAddProductItemForms()).willReturn(itemForms);
    }

    @Test
    void getCart() {
        //given - add Product
        Product productReturned = productService.addProduct(sellerId, addProductForm);

        //given - add Cart
        Long customerId = 1L;
        AddProductCartForm addProductCartForm = mock(AddProductCartForm.class);
        AddCartProductItemForm addCartProductItemForm = mock(AddCartProductItemForm.class);

        given(addProductCartForm.getId()).willReturn(productReturned.getId());
        given(addProductCartForm.getName()).willReturn("testProduct");
        given(addProductCartForm.getDescription()).willReturn("testDescription");
        given(addProductCartForm.getAddCartProductItemForms()).willReturn(List.of(addCartProductItemForm));
        given(addCartProductItemForm.getProductId()).willReturn(productReturned.getProductItems().get(0).getId());
        given(addCartProductItemForm.getName()).willReturn("testProductItem");
        given(addCartProductItemForm.getPrice()).willReturn(1000);
        given(addCartProductItemForm.getCount()).willReturn(1);

        Cart cartAdded = cartService.addCart(customerId, addProductCartForm);

        //when
        Cart cartReturned = cartService.getCart(customerId);

        //then
        assertEquals(customerId, cartReturned.getCustomerId());
        assertEquals(productReturned.getId(), cartReturned.getProducts().get(0).getId());
        assertEquals(productReturned.getName(), cartReturned.getProducts().get(0).getName());
        assertEquals(productReturned.getDescription(), cartReturned.getProducts().get(0).getDescription());
        assertEquals(productReturned.getProductItems().get(0).getId(), cartReturned.getProducts().get(0).getProductItems().get(0).getId());
        assertEquals(productReturned.getProductItems().get(0).getName(), cartAdded.getProducts().get(0).getProductItems().get(0).getName());
        assertEquals(productReturned.getProductItems().get(0).getPrice(), cartAdded.getProducts().get(0).getProductItems().get(0).getPrice());
        assertEquals(1, cartAdded.getProducts().get(0).getProductItems().get(0).getCount());
    }

    @Test
    void putCart() {
        Long customerId = 2L;
        //given
        Cart cart = mock(Cart.class);
        given(cart.getCustomerId()).willReturn(customerId);

        //when
        cartService.putCart(customerId, cart);

        //then
        Cart cartReturned = cartService.getCart(customerId);

        assertEquals(customerId, cartReturned.getCustomerId());

    }



    @Autowired
    ProductService productService;

    @Test
    void addCart() {

        //given - add Product
        Product productReturned = productService.addProduct(sellerId, addProductForm);

        //given - add Cart
        Long customerId = 3L;
        AddProductCartForm addProductCartForm = mock(AddProductCartForm.class);
        AddCartProductItemForm addCartProductItemForm = mock(AddCartProductItemForm.class);

        given(addProductCartForm.getId()).willReturn(productReturned.getId());
        given(addProductCartForm.getName()).willReturn("testProduct");
        given(addProductCartForm.getDescription()).willReturn("testDescription");
        given(addProductCartForm.getAddCartProductItemForms()).willReturn(List.of(addCartProductItemForm));
        given(addCartProductItemForm.getProductId()).willReturn(productReturned.getProductItems().get(0).getId());
        given(addCartProductItemForm.getName()).willReturn("testProductItem");
        given(addCartProductItemForm.getPrice()).willReturn(1000);
        given(addCartProductItemForm.getCount()).willReturn(1);

        //when
        Cart cartAdded = cartService.addCart(customerId, addProductCartForm);

        assertEquals(customerId, cartAdded.getCustomerId());
        assertEquals(productReturned.getId(), cartAdded.getProducts().get(0).getId());
        assertEquals(productReturned.getName(), cartAdded.getProducts().get(0).getName());
        assertEquals(productReturned.getDescription(), cartAdded.getProducts().get(0).getDescription());
        assertEquals(productReturned.getProductItems().get(0).getId(), cartAdded.getProducts().get(0).getProductItems().get(0).getId());
        assertEquals(productReturned.getProductItems().get(0).getName(), cartAdded.getProducts().get(0).getProductItems().get(0).getName());
        assertEquals(productReturned.getProductItems().get(0).getPrice(), cartAdded.getProducts().get(0).getProductItems().get(0).getPrice());
        assertEquals(1, cartAdded.getProducts().get(0).getProductItems().get(0).getCount());
    }
}