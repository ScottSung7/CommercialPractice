package com.example.orderapi.web.controller;

import com.example.orderapi.application.service.ProductSearchService;
import com.example.orderapi.domain.model.dto.ProductDto;
import com.example.orderapi.web.validation.exception.OrderErrorCode;
import com.example.orderapi.web.validation.exception.OrderException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.orderapi.web.validation.exception.OrderErrorCode.WORKING_ON;

@RestController
@RequestMapping("/search/product")
@RequiredArgsConstructor
public class SearchController {
    private final ProductSearchService productSearchService;

//    @GetMapping
//    @Parameter(name = "name", description = "상품명", example = "Apple Macbook")
//    public ResponseEntity<List<ProductDto>> searchByName(@RequestParam String name){
//        return ResponseEntity.ok(
//                productSearchService.searchByName(name).stream()
//                        .map(ProductDto::withoutItemsfrom).collect(Collectors.toList())
//        );
//    }

    @GetMapping("/detail")
    @Parameter(name = "productId", description = "상품 ID", example = "1")
    @Operation(summary = "상품 상세 정보 조회",
              description = "상품에 등록된 아이템 정보를 조회합니다.")
    public ResponseEntity<ProductDto> getDetail(@RequestParam Long productId){
        return ResponseEntity.ok(
                ProductDto.from(productSearchService.getByProductId(productId))
        );
    }


}
