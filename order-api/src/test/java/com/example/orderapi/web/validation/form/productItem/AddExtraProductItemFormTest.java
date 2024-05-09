package com.example.orderapi.web.validation.form.productItem;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class AddExtraProductItemFormTest {

    @Mock
    AddExtraProductItemForm addExtraProductItemForm;

    @Test
    void getProductId() {
        given(addExtraProductItemForm.getProductId()).willReturn(1L);
        assertEquals(addExtraProductItemForm.getProductId(), 1L);

    }
}