package com.example.orderapi.web.validation.form.product;

import com.example.orderapi.web.validation.form.productItem.AddProductItemForm;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class AddProductFormTest {

    @Mock
    AddProductForm addProductForm;

    @Test
    void getName() {
        given(addProductForm.getName()).willReturn("testProduct");
        assertEquals(addProductForm.getName(), "testProduct");
    }

    @Test
    void getDescription() {
        given(addProductForm.getDescription()).willReturn("testDescription");
        assertEquals(addProductForm.getDescription(), "testDescription");
    }

    @Test
    void getAddProductItemForms() {
        AddProductItemForm addProductItemForm = mock(AddProductItemForm.class);
        given(addProductItemForm.getName()).willReturn("testProductItem");
        given(addProductForm.getAddProductItemForms()).willReturn(List.of(addProductItemForm));

        assertEquals(addProductItemForm.getName(), addProductForm.getAddProductItemForms().get(0).getName());
    }


}