package com.example.orderapi.web.controller;

import com.example.orderapi.application.service.ProductItemService;
import com.example.orderapi.application.service.ProductService;
import com.example.orderapi.domain.model.dto.ProductDto;
import com.example.orderapi.domain.model.dto.ProductItemDto;
import com.example.orderapi.web.validation.form.AddProductForm;
import com.example.orderapi.web.validation.form.AddProductItemForm;
import com.example.orderapi.web.validation.form.UpdateProductForm;
import com.example.orderapi.web.validation.form.UpdateProductItemForm;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/seller/product")
@RequiredArgsConstructor
public class SellerProductController {

    private final ProductService productService;
    private final ProductItemService productItemService;
    @PostMapping
    public ResponseEntity<ProductDto> addProduct(@RequestBody AddProductForm form){
        return ResponseEntity.ok(ProductDto.from(productService.addProduct(1L, form)));
    }

    @PostMapping("/item")
    public ResponseEntity<ProductDto> addProductItem(@RequestBody AddProductItemForm form){
        return ResponseEntity.ok(ProductDto.from(productItemService.addProductItem(1L, form)));
    }

    @PutMapping
    public ResponseEntity<ProductDto> updateProduct(@RequestBody UpdateProductForm form){
        return ResponseEntity.ok(ProductDto.from(productService.updateProduct(1L, form)));
    }

    @PutMapping("/item")
    public ResponseEntity<ProductItemDto> updateProductItem(@RequestBody UpdateProductItemForm form){
        return ResponseEntity.ok(ProductItemDto.from(productItemService.updateProductItem(1L, form)));
    }

    @DeleteMapping
    public ResponseEntity<Void> updateProduct(@RequestParam Long id){
        productService.deleteProduct(1L, id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/item")
    public ResponseEntity<Void> updateProductItem(@RequestParam Long id ){
        productItemService.deleteProductItem(1L, id);
        return ResponseEntity.ok().build();
    }
}
