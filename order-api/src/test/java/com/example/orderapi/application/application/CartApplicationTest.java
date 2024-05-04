package com.example.orderapi.application.application;

import com.example.orderapi.application.service.ProductService;
import com.example.orderapi.domain.model.Product;
import com.example.orderapi.domain.model.redis.Cart;
import com.example.orderapi.web.validation.exception.OrderErrorCode;
import com.example.orderapi.web.validation.exception.OrderException;
import com.example.orderapi.web.validation.form.cart.AddCartProductItemForm;
import com.example.orderapi.web.validation.form.cart.AddProductCartForm;
import com.example.orderapi.web.validation.form.product.AddProductForm;
import com.example.orderapi.web.validation.form.productItem.AddProductItemForm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.withSettings;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@Transactional
class CartApplicationTest {

    @Autowired
    CartApplication cartApplication;
    @Autowired
    ProductService productService;

    private Long sellerId;
    private Long customerId;
    @Mock
    AddProductForm addProductForm;
    @Mock
    AddProductItemForm addProductItemForm;
    @Mock
    AddProductCartForm addProductCartForm;
    @Mock
    AddCartProductItemForm addCartProductItemForm;
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

        //addCart
        customerId = 1L;

        addProductCartForm = mock(AddProductCartForm.class);
        addCartProductItemForm = mock(AddCartProductItemForm.class);

        given(addProductCartForm.getName()).willReturn("testProduct");
        given(addProductCartForm.getDescription()).willReturn("testDescription");
        given(addProductCartForm.getAddCartProductItemForms()).willReturn(List.of(addCartProductItemForm));
        given(addCartProductItemForm.getName()).willReturn("testProductItem");
        given(addCartProductItemForm.getPrice()).willReturn(1000);
        given(addCartProductItemForm.getCount()).willReturn(1);
    }

    @Test
    void addCart() {
        //given - addProduct
        Product productReturned = productService.addProduct(sellerId, addProductForm);

        //given - addCart
        given(addProductCartForm.getId()).willReturn(productReturned.getId());
        given(addCartProductItemForm.getProductId()).willReturn(productReturned.getProductItems().get(0).getId());

        //when
        Cart cartAdded = cartApplication.addCart(customerId, addProductCartForm);

        //then
        assertEquals(customerId, cartAdded.getCustomerId());
        assertEquals(productReturned.getId(), cartAdded.getProducts().get(0).getId());
        assertEquals(productReturned.getName(), cartAdded.getProducts().get(0).getName());
        assertEquals(productReturned.getDescription(), cartAdded.getProducts().get(0).getDescription());
        assertEquals(productReturned.getProductItems().get(0).getId(), cartAdded.getProducts().get(0).getProductItems().get(0).getId());
        assertEquals(productReturned.getProductItems().get(0).getName(), cartAdded.getProducts().get(0).getProductItems().get(0).getName());
        assertEquals(productReturned.getProductItems().get(0).getPrice(), cartAdded.getProducts().get(0).getProductItems().get(0).getPrice());
        assertEquals(1, cartAdded.getProducts().get(0).getProductItems().get(0).getCount());
    }
    @Test
    void addCart_NOT_FOUND_PRODUCT(){
        //given
        Long sellerId = 1L;
        AddProductCartForm addProductCartForm = mock(AddProductCartForm.class);

        //when
        OrderException exception = assertThrows(OrderException.class, () ->
                        cartApplication.addCart(sellerId, addProductCartForm));

        //then
        assertEquals(OrderErrorCode.NOT_FOUND_PRODUCT, exception.getOrderErrorCode());
    }
    @Test
    void addCart_ITEM_COUNT_NOT_ENOUGH(){
        Long sellerId = 1L;
        AddProductForm addProductForm = mock(AddProductForm.class);
        AddProductItemForm addProductItemForm = mock(AddProductItemForm.class);

        given(addProductForm.getName()).willReturn("testProduct-addCart");
        given(addProductForm.getDescription()).willReturn("testDescription");

        given(addProductItemForm.getName()).willReturn("testProductItem");
        given(addProductItemForm.getPrice()).willReturn(1000);
        given(addProductItemForm.getCount()).willReturn(10);

        List<AddProductItemForm> itemForms = new ArrayList<>();
        itemForms.add(addProductItemForm);
        given(addProductForm.getAddProductItemForms()).willReturn(itemForms);

        Product productReturned = productService.addProduct(sellerId, addProductForm);

    }

    @Test
    void updateCart() {
    }

    @Test
    void getCart() {
        //given - add Product
        Product productReturned = productService.addProduct(sellerId, addProductForm);

        //given - add Cart
        given(addProductCartForm.getId()).willReturn(productReturned.getId());
        given(addCartProductItemForm.getProductId()).willReturn(productReturned.getProductItems().get(0).getId());
        Cart cartAdded = cartApplication.addCart(customerId, addProductCartForm);

        //when
        Cart cartReturned = cartApplication.getCart(customerId);

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
    void clearCart() {
        //given - add Product
        Product productReturned = productService.addProduct(sellerId, addProductForm);
         String emptyMessage = "장바구니가 비어있습니다.";
        //given - add Cart
        given(addProductCartForm.getId()).willReturn(productReturned.getId());
        given(addCartProductItemForm.getProductId()).willReturn(productReturned.getProductItems().get(0).getId());
        Cart cartAdded = cartApplication.addCart(customerId, addProductCartForm);

        //when
        cartApplication.clearCart(customerId);

        //then
        Cart cart = cartApplication.getCart(customerId);
        assertTrue(cart.checkEmptyCart());
    }

    @Test
    void refreshCart() {
    }
}