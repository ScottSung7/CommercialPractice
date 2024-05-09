package com.example.orderapi.web.validation.form.product;

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
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Getter
public class AddProductForm {

    @NotBlank(message = "상품명을 입력해주세요.")
    @Schema(description = "상품명", example = "Apple Macbook")
    private String name;

    @NotBlank(message = "상품 설명을 입력해주세요.")
    @Schema(description = "상품 설명", example = "애플 컴퓨터")
    private String description;

    @Valid
    @NotEmpty(message = "상품 아이템을 입력해주세요.")
    @Schema(name="addProductItemForms", description = "상품 아이템 리스트")
    private List<AddProductItemForm> addProductItemForms;

}
