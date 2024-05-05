package com.example.orderapi.web.validation.form.cart;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class AddProductCartFormTest {

    @Mock
    AddProductCartForm addProductCartForm;
    @Mock
    AddCartProductItemForm addCartProductItemForm;

    @Test
    void getId() {
        given(addProductCartForm.getId()).willReturn(1L);
        assertEquals(addProductCartForm.getId(), 1L);
    }

    @Test
    void getName() {
        given(addProductCartForm.getName()).willReturn("testProduct");
        assertEquals(addProductCartForm.getName(), "testProduct");
    }

    @Test
    void getDescription() {
        given(addProductCartForm.getDescription()).willReturn("testDescription");
        assertEquals(addProductCartForm.getDescription(), "testDescription");
    }

    @Test
    void getAddCartProductItemForms() {
        given(addCartProductItemForm.getName()).willReturn("testProductItem");
        given(addProductCartForm.getAddCartProductItemForms()).willReturn(List.of(addCartProductItemForm));

        assertEquals(addCartProductItemForm.getName(), addProductCartForm.getAddCartProductItemForms().get(0).getName());
    }
}