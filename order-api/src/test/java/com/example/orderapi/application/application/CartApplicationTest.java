package com.example.orderapi.application.application;

import com.example.orderapi.application.service.ProductItemService;
import com.example.orderapi.application.service.ProductSearchService;
import com.example.orderapi.application.service.ProductService;
import com.example.orderapi.domain.model.Product;
import com.example.orderapi.domain.model.redis.Cart;
import com.example.orderapi.web.validation.exception.OrderErrorCode;
import com.example.orderapi.web.validation.exception.OrderException;
import com.example.orderapi.web.validation.form.cart.AddCartProductItemForm;
import com.example.orderapi.web.validation.form.cart.AddProductCartForm;
import com.example.orderapi.web.validation.form.product.AddProductForm;
import com.example.orderapi.web.validation.form.product.UpdateProductForm;
import com.example.orderapi.web.validation.form.productItem.AddProductItemForm;
import com.example.orderapi.web.validation.form.productItem.UpdateProductItemForm;
import org.junit.jupiter.api.AfterEach;
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

import static com.example.orderapi.web.validation.exception.OrderErrorCode.ITEM_COUNT_NOT_ENOUGH;
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

        addProductCartForm = mock(AddProductCartForm.class, withSettings().lenient());
        addCartProductItemForm = mock(AddCartProductItemForm.class, withSettings().lenient());

        given(addProductCartForm.getName()).willReturn("testProduct");
        given(addProductCartForm.getDescription()).willReturn("testDescription");
        given(addProductCartForm.getAddCartProductItemForms()).willReturn(List.of(addCartProductItemForm));
        given(addCartProductItemForm.getName()).willReturn("testProductItem");
        given(addCartProductItemForm.getPrice()).willReturn(1000);
        given(addCartProductItemForm.getCount()).willReturn(1);

    }
    @AfterEach
    public void after(){
        cartApplication.clearCart(customerId);
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
    void addCat_ProductItem_Not_Enough_Cnt(){
        //given - addProduct
        Product productReturned = productService.addProduct(sellerId, addProductForm);
        //given - addCart
        given(addProductCartForm.getId()).willReturn(productReturned.getId());
        given(addCartProductItemForm.getProductId()).willReturn(productReturned.getProductItems().get(0).getId());
        given(addCartProductItemForm.getCount()).willReturn(productReturned.getProductItems().get(0).getCount() + 1);

        //when
        OrderException exception = assertThrows(OrderException.class, () ->
                        cartApplication.addCart(customerId, addProductCartForm));
        //then
        assertEquals(ITEM_COUNT_NOT_ENOUGH, exception.getOrderErrorCode());
    }
    @Test
    void addCart_NOT_FOUND_PRODUCT(){
        //given
        AddProductCartForm addProductCartForm = mock(AddProductCartForm.class);

        //when
        OrderException exception = assertThrows(OrderException.class, () ->
                        cartApplication.addCart(sellerId, addProductCartForm));
        //then
        assertEquals(OrderErrorCode.NOT_FOUND_PRODUCT, exception.getOrderErrorCode());
    }
    @Test
    void addCart_ITEM_COUNT_NOT_ENOUGH(){
        //given - addProduct
        Product productReturned = productService.addProduct(sellerId, addProductForm);
        //given - addCart
        given(addProductCartForm.getId()).willReturn(productReturned.getId());
        given(addCartProductItemForm.getProductId()).willReturn(productReturned.getProductItems().get(0).getId());
        given(addCartProductItemForm.getCount()).willReturn(productReturned.getProductItems().get(0).getCount() + 1);

        //when
        OrderException exception = assertThrows(OrderException.class, () ->
                        cartApplication.addCart(customerId, addProductCartForm));
        //then
        assertEquals(ITEM_COUNT_NOT_ENOUGH, exception.getOrderErrorCode());
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
    void getCart_messages_item_price_changed(){
        //given - add Product
        Product productReturned = productService.addProduct(sellerId, addProductForm);
        //given - add Cart
        given(addProductCartForm.getId()).willReturn(productReturned.getId());
        given(addCartProductItemForm.getProductId()).willReturn(productReturned.getProductItems().get(0).getId());
        given(addCartProductItemForm.getPrice()).willReturn(100);
        Cart cartAdded = cartApplication.addCart(customerId, addProductCartForm);

        //when
        Cart cartReturned = cartApplication.getCart(customerId);

        //then
        assertEquals(1, cartReturned.getMessages().size());
        assertEquals(productReturned.getName() + " 상품의 변동 사항 : " +
                        productReturned.getProductItems().get(0).getName() +  " 가격이 변동되었습니다."
                        , cartReturned.getMessages().get(0));
    }
    @Test
    void getCart_messages_item_count_not_enough(){
        //given - add Product
        Product productReturned = productService.addProduct(sellerId, addProductForm);
        //given - add Cart
        given(addProductCartForm.getId()).willReturn(productReturned.getId());
        given(addCartProductItemForm.getProductId()).willReturn(productReturned.getProductItems().get(0).getId());
        given(addCartProductItemForm.getPrice()).willReturn(1000);
        given(addCartProductItemForm.getCount()).willReturn(8);
        Cart cartAdded = cartApplication.addCart(customerId, addProductCartForm);

        //given - product count changed
        UpdateProductForm updateProductForm = mock(UpdateProductForm.class);
        given(updateProductForm.getId()).willReturn(productReturned.getId());
        given(updateProductForm.getName()).willReturn("testProduct");
        given(updateProductForm.getDescription()).willReturn("testDescription");

        UpdateProductItemForm updateProductItemForm = mock(UpdateProductItemForm.class, withSettings().lenient());
        given(updateProductItemForm.getProductId()).willReturn(productReturned.getId());
        given(updateProductItemForm.getItemId()).willReturn(productReturned.getProductItems().get(0).getId());
        given(updateProductItemForm.getName()).willReturn("testProductItem");
        given(updateProductItemForm.getPrice()).willReturn(1000);
        given(updateProductItemForm.getCount()).willReturn(5);
        given(updateProductForm.getUpdateProductItemForms()).willReturn(List.of(updateProductItemForm));

        productService.updateProduct(sellerId, updateProductForm);

        //when
        Cart cartReturned = cartApplication.getCart(customerId);

        //then
        assertEquals(1, cartReturned.getMessages().size());
        assertEquals(productReturned.getName() + " 상품의 변동 사항 : " +
                        productReturned.getProductItems().get(0).getName() +  " 수량이 부족하여 구매 가능한 최대치로 변경되었습니다."
                , cartReturned.getMessages().get(0));
    }
    @Test
    void getCart_messages_price_changed_and_item_count_not_enough(){
        //given - add Product
        Product productReturned = productService.addProduct(sellerId, addProductForm);
        //given - add Cart
        given(addProductCartForm.getId()).willReturn(productReturned.getId());
        given(addCartProductItemForm.getProductId()).willReturn(productReturned.getProductItems().get(0).getId());
        given(addCartProductItemForm.getPrice()).willReturn(1000);
        given(addCartProductItemForm.getCount()).willReturn(8);
        Cart cartAdded = cartApplication.addCart(customerId, addProductCartForm);

        //given - product count changed
        UpdateProductForm updateProductForm = mock(UpdateProductForm.class);
        given(updateProductForm.getId()).willReturn(productReturned.getId());
        given(updateProductForm.getName()).willReturn("testProduct");
        given(updateProductForm.getDescription()).willReturn("testDescription");

        UpdateProductItemForm updateProductItemForm = mock(UpdateProductItemForm.class, withSettings().lenient());
        given(updateProductItemForm.getProductId()).willReturn(productReturned.getId());
        given(updateProductItemForm.getItemId()).willReturn(productReturned.getProductItems().get(0).getId());
        given(updateProductItemForm.getName()).willReturn("testProductItem");
        given(updateProductItemForm.getPrice()).willReturn(8000);
        given(updateProductItemForm.getCount()).willReturn(5);
        given(updateProductForm.getUpdateProductItemForms()).willReturn(List.of(updateProductItemForm));

        productService.updateProduct(sellerId, updateProductForm);

        //when
        Cart cartReturned = cartApplication.getCart(customerId);

        //then
        assertEquals(1, cartReturned.getMessages().size());
        assertEquals(productReturned.getName() + " 상품의 변동 사항 : " +
                        productReturned.getProductItems().get(0).getName() +  " 가격변동, 수량이 부족하여 구매 가능한 최대치로 변경되었습니다."
                , cartReturned.getMessages().get(0));
    }
//    @Autowired
//    ProductItemService productItemService;
//    @Test
//    void getCart_product_deleted(){
//        //given - add Product
//        productService.addProduct(sellerId, addProductForm);
//
//        //given - add Cart
//        given(addProductCartForm.getId()).willReturn(1L);
//        given(addCartProductItemForm.getProductId()).willReturn(1L);
//        given(addCartProductItemForm.getPrice()).willReturn(1000);
//        given(addCartProductItemForm.getCount()).willReturn(8);
//        Cart cartAdded = cartApplication.addCart(customerId, addProductCartForm);
//
//
//        //given - product count changed
//        productItemService.deleteProductItem(sellerId, 1L);
//
//        //when
//        Cart cartReturned = cartApplication.getCart(customerId);
//
//        //then
//        assertEquals(1, cartReturned.getMessages().size());
//        assertEquals(addProductCartForm.getName() + " 상품의 변동 사항 : " +
//                        addProductCartForm.getAddCartProductItemForms().get(0).getName() +  " 상품의 옵션이 모두 없어져 구매가 불가능합니다."
//                , cartReturned.getMessages().get(0));
//    }


    @Test
    void clearCart() {
        //given - add Product
        Product productReturned = productService.addProduct(sellerId, addProductForm);
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
}