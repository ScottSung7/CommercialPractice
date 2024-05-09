package com.example.orderapi.web.validation.form.productItem;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class UpdateProductItemForm {

    @NotNull(message = "상품 ID를 입력해주세요.")
    @Schema(description = "아이템 ID", example = "1")
    private Long itemId;

    @NotNull(message = "상품 ID를 입력해주세요.")
    @Schema(description = "제품 ID", example = "1")
    private Long productId;

    @NotNull(message = "아이템 이름을 입력해주세요.")
    @Schema(description = "아이템 이름", example = "변경된 이름")
    private String name;

    @NotNull(message = "아이템 가격을 입력해주세요.")
    @Schema(description = "아이템 가격", example = "1")
    private Integer price;

    @NotNull(message = "아이템 수량을 입력해주세요.")
    @Schema(description = "아이템 수량", example = "1")
    private Integer count;

}
