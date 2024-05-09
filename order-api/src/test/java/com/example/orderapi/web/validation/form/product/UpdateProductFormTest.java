package com.example.orderapi.web.validation.form.product;

import com.example.orderapi.web.validation.form.productItem.UpdateProductItemForm;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class UpdateProductFormTest {

    @Mock
    UpdateProductForm updateProductForm;
    @Mock
    UpdateProductItemForm updateProductItemForm;

    @Test
    void getId() {
        given(updateProductForm.getId()).willReturn(1L);
        assertEquals(updateProductForm.getId(), 1L);
    }

    @Test
    void getName() {
        given(updateProductForm.getName()).willReturn("testProduct");
        assertEquals(updateProductForm.getName(), "testProduct");
    }

    @Test
    void getDescription() {
        given(updateProductForm.getDescription()).willReturn("testDescription");
        assertEquals(updateProductForm.getDescription(), "testDescription");
    }

    @Test
    void getUpdateProductItemForms() {
        given(updateProductItemForm.getName()).willReturn("testProductItem");
        given(updateProductForm.getUpdateProductItemForms()).willReturn(List.of(updateProductItemForm));

        assertEquals(updateProductItemForm.getName(), updateProductForm.getUpdateProductItemForms().get(0).getName());
    }
}