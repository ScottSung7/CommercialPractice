package com.example.orderapi.web.validation.form.productItem;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class UpdateProductItemFormTest {

    @Mock
    UpdateProductItemForm updateProductItemForm;

    @Test
    void getItemId() {
        given(updateProductItemForm.getItemId()).willReturn(1L);
        assertEquals(updateProductItemForm.getItemId(), 1L);
    }

    @Test
    void getProductId() {
        given(updateProductItemForm.getProductId()).willReturn(1L);
        assertEquals(updateProductItemForm.getProductId(), 1L);
    }

    @Test
    void getName() {
        given(updateProductItemForm.getName()).willReturn("testProductItem");
        assertEquals(updateProductItemForm.getName(), "testProductItem");
    }

    @Test
    void getPrice() {
        given(updateProductItemForm.getPrice()).willReturn(1000);
        assertEquals(updateProductItemForm.getPrice(), 1000);
    }

    @Test
    void getCount() {
        given(updateProductItemForm.getCount()).willReturn(10);
        assertEquals(updateProductItemForm.getCount(), 10);
    }
}