package com.example.orderapi.application.service;

import com.example.orderapi.domain.model.Product;
import com.example.orderapi.domain.model.ProductItem;
import com.example.orderapi.web.validation.exception.OrderException;
import com.example.orderapi.web.validation.form.product.AddProductForm;
import com.example.orderapi.web.validation.form.productItem.AddExtraProductItemForm;
import com.example.orderapi.web.validation.form.productItem.AddProductItemForm;
import com.example.orderapi.web.validation.form.productItem.UpdateProductItemForm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.example.orderapi.web.validation.exception.OrderErrorCode.NOT_FOUND_PRODUCT;
import static com.example.orderapi.web.validation.exception.OrderErrorCode.SAME_ITEM_NAME;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.withSettings;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Transactional
class ProductItemServiceTest {

    @Autowired
    private ProductItemService productItemService;
    @Autowired
    private ProductService productService;
    private Long sellerId;
    @Mock
    static AddProductForm addProductForm;
    @Mock
    static AddProductItemForm addProductItemForm;

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
    void addProductItem() {
        AddExtraProductItemForm addExtraProductItemForm = mock(AddExtraProductItemForm.class);

        //given
        Product product = productService.addProduct(sellerId, addProductForm);
        given(addExtraProductItemForm.getProductId()).willReturn(product.getId());
        given(addExtraProductItemForm.getName()).willReturn("testExtraProductItem");
        given(addExtraProductItemForm.getPrice()).willReturn(1000);
        given(addExtraProductItemForm.getCount()).willReturn(10);

        //when
        Product productChanged = productItemService.addProductItem(1L, addExtraProductItemForm);

        assertEquals(2, productChanged.getProductItems().size());
        assertEquals("testExtraProductItem", productChanged.getProductItems().get(1).getName());
        assertEquals(1000, productChanged.getProductItems().get(1).getPrice());
        assertEquals(10, productChanged.getProductItems().get(1).getCount());
    }
    @Test
    void addProductItem_NOT_FOUND_PRODUCT(){
        //given
        AddExtraProductItemForm addExtraProductItemForm = mock(AddExtraProductItemForm.class);
        given(addExtraProductItemForm.getProductId()).willReturn(0L);
        //when
        OrderException exception = assertThrows(OrderException.class,
                () -> productItemService.addProductItem(1L, addExtraProductItemForm));
        //then
        assertEquals(NOT_FOUND_PRODUCT, exception.getOrderErrorCode());
    }
    @Test
    void addProductItem_SAME_ITEM_NAME(){
        //given
        AddExtraProductItemForm addExtraProductItemForm = mock(AddExtraProductItemForm.class);
        Product product = productService.addProduct(sellerId, addProductForm);
        given(addExtraProductItemForm.getProductId()).willReturn(product.getId());
        given(addExtraProductItemForm.getName()).willReturn("testProductItem");

        //when
        OrderException exception = assertThrows(OrderException.class,
                () -> productItemService.addProductItem(1L, addExtraProductItemForm));
        //then
        assertEquals(SAME_ITEM_NAME, exception.getOrderErrorCode());
    }

    @Test
    void updateProductItem() {
        UpdateProductItemForm updateProductItemForm = mock(UpdateProductItemForm.class);
        //given
        Product product = productService.addProduct(sellerId, addProductForm);
        given(updateProductItemForm.getItemId()).willReturn(product.getProductItems().get(0).getId());
        given(updateProductItemForm.getName()).willReturn("updatedProductItem");
        given(updateProductItemForm.getPrice()).willReturn(2000);
        given(updateProductItemForm.getCount()).willReturn(20);

        //when
        ProductItem updatedItem = productItemService.updateProductItem(product.getProductItems().get(0).getId(), updateProductItemForm);

        //then
        assertEquals("updatedProductItem", updatedItem.getName());
        assertEquals(2000, updatedItem.getPrice());
        assertEquals(20, updatedItem.getCount());
    }
    @Test
    void updateProductItem_NOT_FOUND_ITEM(){
        UpdateProductItemForm updateProductItemForm = mock(UpdateProductItemForm.class);
        //given

        //given
        given(updateProductItemForm.getItemId()).willReturn(0L);
        //when
        OrderException exception = assertThrows(OrderException.class,
                () -> productItemService.updateProductItem(sellerId, updateProductItemForm));
        //then
        assertEquals(NOT_FOUND_PRODUCT, exception.getOrderErrorCode());
    }

    @Test
    void deleteProductItem() {
    }
}