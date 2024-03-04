package com.example.order_api.domain.model;

import com.example.order_api.web.validation.form.AddProductForm;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.Audited;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Audited //변할때마다 변화 여기 저장
@AuditOverride(forClass = BaseEntity.class)
public class Product extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long sellerId;
    private String name;
    private String description; //이미지등 추가 필요

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="product_id")
    private List<ProductItem> productItems = new ArrayList<>();

    public static Product of(Long sellerId, AddProductForm addProductForm){
        return Product.builder()
                .sellerId(sellerId)
                .name(addProductForm.getName())
                .description(addProductForm.getDescription())
                .productItems(addProductForm.getAddProductItemFormList().stream()
                        .map(addProductItemForm -> ProductItem.of(sellerId, addProductItemForm)).collect(Collectors.toList()))
                .build();
    }
}
