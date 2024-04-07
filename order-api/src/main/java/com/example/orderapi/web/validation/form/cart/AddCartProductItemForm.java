package com.example.orderapi.web.validation.form.cart;


import com.example.orderapi.web.validation.form.productItem.AddProductItemForm;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class AddCartProductItemForm extends AddProductItemForm {

    //AddExtraProductItemForm과 중복되지만 쓰임이 달라 생성.

    @Schema(description = "상품 ID", example = "1")
    @NotNull(message = "상품 ID를 입력해주세요.")
    private Long productId;

}
