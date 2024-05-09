package com.example.orderapi.web.controller;

import com.example.orderapi.application.service.ProductItemService;
import com.example.orderapi.application.service.ProductService;
import com.example.orderapi.application.service.ProductServiceImpl;
import com.example.orderapi.config.common.validation.LoginCheckMSA;
import com.example.orderapi.config.common.SpringSecurity.id.UserPrincipalDetails;
import com.example.orderapi.domain.model.Product;
import com.example.orderapi.domain.model.dto.ProductDto;
import com.example.orderapi.domain.model.dto.ProductItemDto;
import com.example.orderapi.web.validation.form.productItem.AddExtraProductItemForm;
import com.example.orderapi.web.validation.form.product.AddProductForm;
import com.example.orderapi.web.validation.form.product.UpdateProductForm;
import com.example.orderapi.web.validation.form.productItem.UpdateProductItemForm;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/seller/product")
@RequiredArgsConstructor
public class SellerProductController {

    private final ProductService productService;
    private final ProductItemService productItemService;

    @PostMapping
    @Schema(name = "AddProductForm", implementation = AddProductForm.class)
    public ResponseEntity<ProductDto> addProduct(@Validated @RequestBody AddProductForm form, Authentication authentication) {

        UserPrincipalDetails seller = LoginCheckMSA.sellerCheck(authentication);

        Product product = productService.addProduct(seller.getId(), form);
        return ResponseEntity.ok(ProductDto.from(product));
    }

    @PostMapping("/item")
    @Schema(name = "AddExtraProductItemForm", implementation = AddExtraProductItemForm.class)
    @Operation(summary = "addProduct 후 확인 가능합니다.",
               description = "NOT_FOUND_PRODUCT 시 addProduct한 product의 id를 Request body에 넣어 주세요." )
    public ResponseEntity<ProductDto> addProductItem(@Validated @RequestBody AddExtraProductItemForm form, Authentication authentication){
        UserPrincipalDetails seller = LoginCheckMSA.sellerCheck(authentication);

        return ResponseEntity.ok(ProductDto.from(productItemService.addProductItem(seller.getId(), form)));
    }

    @PutMapping
    @Operation(summary = "addProduct 후 확인 가능합니다.",
            description = "NOT_FOUND_PRODUCT 시 addProduct한 product의 id를 Request body에 넣어 주세요." )
    @Schema(name = "UpdateProductForm", implementation = UpdateProductForm.class)
    public ResponseEntity<ProductDto> updateProduct(@Validated @RequestBody UpdateProductForm form, Authentication authentication){
        UserPrincipalDetails seller = LoginCheckMSA.sellerCheck(authentication);
        return ResponseEntity.ok(ProductDto.from(productService.updateProduct(seller.getId(), form)));
    }

    @PutMapping("/item")
    @Schema(name = "UpdateProductItemForm", implementation = UpdateProductItemForm.class)
    @Operation(summary = "addProduct 후 확인 가능합니다.",
               description = "NOT_FOUND_ITEM 시 addProduct후 받은 product Id 와 item Id를 Request body에 넣어 주세요.")
    public ResponseEntity<ProductItemDto> updateProductItem(@Validated @RequestBody UpdateProductItemForm form, Authentication authentication){

        UserPrincipalDetails seller = LoginCheckMSA.sellerCheck(authentication);

        return ResponseEntity.ok(ProductItemDto.from(productItemService.updateProductItem(seller.getId(), form)));
    }

    @DeleteMapping
    @Operation(summary = "addProduct 후 확인 가능합니다.",
            description = "NOT_FOUND_PRODUCT 시 추가한 product id를 파라미터에 넣어주세요." )
    @Parameter(name = "productId", example = "1")
    public ResponseEntity<Void> DeleteProduct(@RequestParam Long productId, Authentication authentication){

        UserPrincipalDetails seller = LoginCheckMSA.sellerCheck(authentication);
        productService.deleteProduct(seller.getId(), productId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/item")
    @Operation(summary = "addProduct 후 확인 가능합니다.",
            description = "NOT_FOUND_ITEM 시 추가한 item id를 파라미터에 넣어주세요." )
    @Parameter(name = "itemId", example = "1")
    public ResponseEntity<Void> deleteProductItem(@RequestParam Long itemId, Authentication authentication){
        UserPrincipalDetails seller = LoginCheckMSA.sellerCheck(authentication);
        productItemService.deleteProductItem(seller.getId(), itemId);

        return ResponseEntity.ok().build();
    }
    @PostMapping("/myproducts")
    @Operation(summary = "판매자가 등록한 상품 리스트를 조회합니다.")
    public ResponseEntity<List<ProductDto>> getMyProducts(Authentication authentication){
        UserPrincipalDetails seller = LoginCheckMSA.sellerCheck(authentication);
        List<Product> products = productService.getMyProducts(seller.getId());

        List<ProductDto> productDTOs = products.stream()
                .map(ProductDto::from).collect(Collectors.toList());

        return ResponseEntity.ok(productDTOs);
    }
}