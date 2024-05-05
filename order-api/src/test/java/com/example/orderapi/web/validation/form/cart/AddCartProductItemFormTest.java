package com.example.orderapi.web.validation.form.cart;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;


@ExtendWith(MockitoExtension.class)
class AddCartProductItemFormTest {
    @Mock
    AddCartProductItemForm addCartProductItemForm;

    @Test
    void getProductId() {
        given(addCartProductItemForm.getProductId()).willReturn(1L);
        assertEquals(addCartProductItemForm.getProductId(), 1L);
    }
}