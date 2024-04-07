package com.example.orderapi.domain.model;

import com.example.orderapi.web.validation.form.cart.AddProductCartForm;
import com.example.orderapi.web.validation.form.product.AddProductForm;
import com.example.orderapi.web.validation.form.productItem.AddProductItemForm;
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
        AddProductItemForm forms = addProductForm.getAddProductItemForms().get(0);
        System.out.println(forms.getName());

        return Product.builder()
                .sellerId(sellerId)
                .name(addProductForm.getName())
                .description(addProductForm.getDescription())
                .productItems(addProductForm.getAddProductItemForms().stream()
                        .map(addProductItemForm -> ProductItem.of(sellerId, addProductItemForm)).collect(Collectors.toList()))
                .build();
    }

    public static Product from(AddProductCartForm form){
        return Product.builder()
                .id(form.getId())
                .name(form.getName())
                .description(form.getDescription())
                .productItems(form.getAddCartProductItemForms().stream()
                        .map(ProductItem::from).collect(Collectors.toList()))
                .build();

    }
}
