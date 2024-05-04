package com.example.orderapi.application.service;

import com.example.orderapi.domain.model.Product;
import com.example.orderapi.web.validation.exception.OrderException;
import com.example.orderapi.web.validation.form.product.AddProductForm;
import com.example.orderapi.web.validation.form.productItem.AddProductItemForm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.example.orderapi.web.validation.exception.OrderErrorCode.NOT_FOUND_PRODUCT;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.withSettings;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@Transactional
class ProductSearchServiceTest {

    @Autowired
    ProductSearchService productSearchService;
    @Autowired
    ProductService productService;
    private Long sellerId;
    @Mock
    static AddProductForm addProductForm;
    @Mock
    static AddProductItemForm addProductItemForm;

    List<AddProductItemForm> itemForms;

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

        itemForms = new ArrayList<>();
        itemForms.add(addProductItemForm);
        given(addProductForm.getAddProductItemForms()).willReturn(itemForms);
    }
    @Test
    void getByProductId() {
        //given
        Product product = productService.addProduct(sellerId, addProductForm);
        Long productId = product.getId();

        //when
        Product productSearched = productSearchService.getByProductId(productId);

        //then
        assertEquals(productSearched.getName(), product.getName());
        assertEquals(productSearched.getDescription(), product.getDescription());
        assertEquals(productSearched.getProductItems().get(0).getName(), product.getProductItems().get(0).getName());
        assertEquals(productSearched.getProductItems().get(0).getPrice(), product.getProductItems().get(0).getPrice());
        assertEquals(productSearched.getProductItems().get(0).getCount(), product.getProductItems().get(0).getCount());
    }
    @Test
    void getByProductId_NOT_FOUND_Product() {
        //given
        Long productId = 0L;
        //when
        OrderException exception = assertThrows(OrderException.class, () -> productSearchService.getByProductId(productId));
        //then
        assertEquals(NOT_FOUND_PRODUCT, exception.getOrderErrorCode());
    }

    @Test
    void getListByProductIds() {
        //given
        Product product = productService.addProduct(sellerId, addProductForm);
        Long productId = product.getId();
        List<Long> productIds = new ArrayList<>();
        productIds.add(productId);

        //when
        List<Product> products = productSearchService.getListByProductIds(productIds);

        //then
        assertEquals(products.get(0).getName(), product.getName());
        assertEquals(products.get(0).getDescription(), product.getDescription());
        assertEquals(products.get(0).getProductItems().get(0).getName(), product.getProductItems().get(0).getName());
        assertEquals(products.get(0).getProductItems().get(0).getPrice(), product.getProductItems().get(0).getPrice());
        assertEquals(products.get(0).getProductItems().get(0).getCount(), product.getProductItems().get(0).getCount());
    }

    @Test
    void getListByProductIds_NOT_FOUND_Product() {
        //given
        List<Long> productIds = new ArrayList<>();
        productIds.add(0L);
        //when
        OrderException exception = assertThrows(OrderException.class, () -> productSearchService.getListByProductIds(productIds));
        //then
        assertEquals(NOT_FOUND_PRODUCT, exception.getOrderErrorCode());
    }

//    @Test
//    void searchByName() {
//        //given
//        Product product = productService.addProduct(sellerId, addProductForm);
//        String name = product.getName();
//        //when
//        List<Product> products = productSearchService.searchByName(name);
//        //then
//        assertEquals(products.get(0).getName(), product.getName());
//        assertEquals(products.get(0).getDescription(), product.getDescription());
//        assertEquals(products.get(0).getProductItems().get(0).getName(), product.getProductItems().get(0).getName());
//        assertEquals(products.get(0).getProductItems().get(0).getPrice(), product.getProductItems().get(0).getPrice());
//        assertEquals(products.get(0).getProductItems().get(0).getCount(), product.getProductItems().get(0).getCount());
//    }
//    @Test
//    void searchByName_NOT_FOUND_Product() {
//        //given
//        String name = "test";
//        //when
//        OrderException exception = assertThrows(OrderException.class, () -> productSearchService.searchByName(name));
//        //then
//        assertEquals(NOT_FOUND_PRODUCT, exception.getOrderErrorCode());
//    }
}