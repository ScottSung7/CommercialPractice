package com.example.orderapi.web.validation.form.product;

import com.example.orderapi.web.validation.form.productItem.UpdateProductItemForm;
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
public class UpdateProductForm {
    @Schema(description = "상품 ID", example = "1")
    @NotNull(message = "상품 ID를 입력해주세요.")
    private Long id;

    @Schema(description = "상품명", example = "MacBook Pro")
    @NotBlank(message = "상품명을 입력해주세요.")
    private String name;

    @Schema(description = "상품 설명", example = "Apple 제품입니다.")
    @NotBlank(message = "상품 설명을 입력해주세요.")
    private String description;

    @Valid
    @Schema(name="updateProductItemForms", description = "상품 아이템 리스트")
    @NotEmpty(message = "상품 아이템을 입력해주세요.")
    private List<UpdateProductItemForm> updateProductItemForms;

}
