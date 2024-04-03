package com.example.orderapi.web.controller;

import com.example.orderapi.application.service.ProductSearchService;
import com.example.orderapi.domain.model.dto.ProductDto;
import com.example.orderapi.web.validation.exception.OrderErrorCode;
import com.example.orderapi.web.validation.exception.OrderException;
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

    @GetMapping
    public ResponseEntity<List<ProductDto>> searchByName(@RequestParam String name){

        throw new OrderException(WORKING_ON);

//        return ResponseEntity.ok(
//                productSearchService.searchByName(name).stream()
//                        .map(ProductDto::withoutItemsfrom).collect(Collectors.toList())
//        );
    }

    @GetMapping("/detail")
    public ResponseEntity<ProductDto> getDetail(@RequestParam Long productId){
        throw new OrderException(WORKING_ON);


//        return ResponseEntity.ok(
//                ProductDto.from(productSearchService.getByProductId(productId))
//        );
    }


}
