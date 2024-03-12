package com.example.orderapi.web.validation.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor //원래는 Getter만 -> 테스트 코드는 Mocking으로
public class AddProductForm {

    private String name;
    private String description;
    private List<AddProductItemForm> addProductItemFormList;
}
