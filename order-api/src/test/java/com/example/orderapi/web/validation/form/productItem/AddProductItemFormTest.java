package com.example.orderapi.web.validation.form.productItem;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class AddProductItemFormTest {

    @Mock
    AddProductItemForm addProductItemForm;
    @Test
    void getName() {
        given(addProductItemForm.getName()).willReturn("testProductItem");
        assertEquals(addProductItemForm.getName(), "testProductItem");
    }

    @Test
    void getPrice() {
        given(addProductItemForm.getPrice()).willReturn(1000);
        assertEquals(addProductItemForm.getPrice(), 1000);
    }

    @Test
    void getCount() {
        given(addProductItemForm.getCount()).willReturn(10);
        assertEquals(addProductItemForm.getCount(), 10);
    }
}