package com.example.orderapi.application.service;

import com.example.orderapi.domain.model.Product;
import com.example.orderapi.domain.model.ProductItem;
import com.example.orderapi.repository.ProductRepository;
import com.example.orderapi.web.validation.exception.OrderException;
import com.example.orderapi.web.validation.form.product.AddProductForm;
import com.example.orderapi.web.validation.form.product.UpdateProductForm;
import com.example.orderapi.web.validation.form.productItem.AddProductItemForm;
import com.example.orderapi.web.validation.form.productItem.UpdateProductItemForm;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.Ordered;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.orderapi.web.validation.exception.OrderErrorCode.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;


@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(MockitoExtension.class)
@Transactional
class ProductServiceTest {

    @Autowired
    ProductService productService;
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
    void addProduct() {
        //given
        //@Before

        //when
        Product productReturned = productService.addProduct(sellerId, addProductForm);

        //then
        assertEquals("testProduct", productReturned.getName());
        assertEquals("testDescription", productReturned.getDescription());
        assertEquals("testProductItem", productReturned.getProductItems().get(0).getName());
        assertEquals(1000, productReturned.getProductItems().get(0).getPrice());
        assertEquals(10, productReturned.getProductItems().get(0).getCount());
    }
    @Test
    void addProduct_SAME_PRODUCT_NAME(){
        //given
        //@Before
        Product product = productService.addProduct(sellerId, addProductForm);

        //when
        OrderException exception
                = assertThrows(OrderException.class, () -> productService.addProduct(sellerId, addProductForm));

        //then
        assertEquals(SAME_PRODUCT_NAME, exception.getOrderErrorCode());
    }
    @Test
    void updateProduct() {

        UpdateProductForm updateProductForm = mock(UpdateProductForm.class);
        UpdateProductItemForm updateProductItemForm = mock(UpdateProductItemForm.class);

        //given
        //addProduct
        Product product = productService.addProduct(sellerId, addProductForm);

        //updateProduct
        given(updateProductForm.getId()).willReturn(product.getId());
        given(updateProductForm.getName()).willReturn("updatedProduct");

        List<UpdateProductItemForm> itemForms = new ArrayList<>();
        given(updateProductItemForm.getItemId()).willReturn(product.getProductItems().get(0).getId());
        given(updateProductItemForm.getName()).willReturn("upDatedProductItem");
        itemForms.add(updateProductItemForm);

        given(updateProductForm.getUpdateProductItemForms()).willReturn(itemForms);

        //when
        Product updatedProduct = productService.updateProduct(sellerId, updateProductForm);

        //then
        ;
        assertTrue(updatedProduct.getName().equals("updatedProduct"));
        assertTrue(updatedProduct.getProductItems().get(0).getName().equals("upDatedProductItem"));
    }
    @Test
    void updateProduct_NOT_FOUND_PRODUCT(){

        UpdateProductForm updateProductForm = mock(UpdateProductForm.class);
        //given
        given(updateProductForm.getId()).willReturn(0L);

        //when
        OrderException exception
                = assertThrows(OrderException.class, () -> productService.updateProduct(sellerId, updateProductForm));

        //then
        assertEquals(NOT_FOUND_PRODUCT, exception.getOrderErrorCode());
    }

    @Test
    void getMyProducts() {

        //given
        Product product = productService.addProduct(sellerId, addProductForm);

        //when
        List<Product> productList = productService.getMyProducts(sellerId);

        //then
        assertEquals("testProduct", productList.get(0).getName());
        assertEquals(1, productList.size());
        assertTrue(productList.get(0).getId().equals(product.getId()));
        assertTrue(productList.get(0).getProductItems().size() == 1);
        assertTrue(productList.get(0).getProductItems().get(0).getId()
                .equals(product.getProductItems().get(0).getId()));
    }
    @Test
    void getMyProducts_NO_PRODUCT(){
        //when
        OrderException exception
                = assertThrows(OrderException.class, () -> productService.getMyProducts(0L));
        //then
        assertEquals(NO_PRODUCT, exception.getOrderErrorCode());
    }

    @Test
    void deleteProduct() {

        //given
        Product product = productService.addProduct(sellerId, addProductForm);

        //when
        productService.deleteProduct(sellerId, product.getId());

        //then
        OrderException exception
                = assertThrows(OrderException.class, () -> productService.getMyProducts(sellerId));
        assertEquals(NO_PRODUCT, exception.getOrderErrorCode());
    }

}