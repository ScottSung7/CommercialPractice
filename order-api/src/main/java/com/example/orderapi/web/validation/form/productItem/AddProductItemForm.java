package com.example.orderapi.web.validation.form.productItem;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.validation.annotation.Validated;

@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class AddProductItemForm {

//    @Schema(description = "상품 ID", example = "1")
//    private Long productId;

    @NotBlank(message = "상품 아이템명을 입력해주세요.")
    @Schema(description = "상품 아이템명", example = "MacBook Pro")
    private String name;

    @Schema(description = "상품 아이템 가격", example = "30000")
    @NotNull(message = "상품 아이템 가격을 입력해주세요.")
    private Integer price;

    @Schema(description = "상품 아이템 수량", example = "10")
    @NotNull(message = "상품 아이템 수량을 입력해주세요.")
    private Integer count;

}
