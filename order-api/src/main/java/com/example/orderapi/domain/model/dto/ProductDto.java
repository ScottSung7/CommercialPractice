package com.example.orderapi.domain.model.dto;

import com.example.orderapi.domain.model.Product;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    private Long id;
    private String name;
    private String description;
    private List<ProductItemDto> items;

    public static ProductDto from(Product product){
        List<ProductItemDto> items = product.getProductItems()
                .stream().map(ProductItemDto::from).collect(Collectors.toList());
        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .items(items)
                .build();
    }

    public static ProductDto withoutItemsfrom(Product product){
        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .build();
    }
}
