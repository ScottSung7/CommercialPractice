package com.example.orderapi.web.validation.form.cart;


import com.example.orderapi.domain.model.ProductItem;
import com.example.orderapi.web.validation.form.productItem.AddProductItemForm;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
public class AddProductCartForm {

    @NotNull(message = "상품 ID를 넣어주세요.")
    @Schema(description = "상품 ID", example = "1")
    private Long id;

    @NotBlank(message = "상품 이름을 넣어주세요.")
    @Schema(description = "상품 이름", example = "Apple Macbook")
    private String name;

    @NotBlank(message = "상품 설명을 넣어주세요.")
    @Schema(description = "상품 설명", example = "애플 컴퓨터")
    private String description;

    @Schema(description = "상품 아이템")
    @NotEmpty(message = "상품 아이템을 넣어주세요.")
    @Valid
    private List<AddCartProductItemForm> addCartProductItemForms;

}
