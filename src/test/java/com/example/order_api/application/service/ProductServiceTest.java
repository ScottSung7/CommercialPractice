package com.example.order_api.application.service;

import com.example.order_api.domain.model.Product;
import com.example.order_api.repository.ProductRepository;
import com.example.order_api.web.validation.form.AddProductForm;
import com.example.order_api.web.validation.form.AddProductItemForm;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @Test
    void ADD_PRODUCT_TEST(){
        Long sellerId = 1L;
        AddProductForm form = makeProductForm("테스트", "테스트용", 3);
        Product p = productService.addProduct(sellerId, form);

        Product result = productRepository.findWithProductItemById(p.getId()).get();

        System.out.println(p.getName());
        System.out.println(result.getName());
        assertNotNull(result);
        assertEquals(result.getName(), "테스트");
        assertEquals(result.getDescription(), "테스트용");
        assertEquals(result.getProductItems().size(), 3);
        assertEquals(result.getProductItems().get(0).getName(), "테스트0");

    }
    private AddProductForm makeProductForm(String name, String description, int itemCount){
        List<AddProductItemForm> itemForms = new ArrayList<>();
        for (int i = 0; i < itemCount; i++) {
            itemForms.add(makeProductItemForm(null, name+i));
        }
        return AddProductForm.builder()
                .name(name)
                .description(description)
                .addProductItemFormList(itemForms)
                .build();

    }
    private static AddProductItemForm makeProductItemForm(Long productId, String name){
        return AddProductItemForm.builder()
                .productId(productId)
                .name(name)
                .price(1000)
                .count(1)
                .build();

    }
}