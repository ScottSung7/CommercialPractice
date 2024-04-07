package com.example.orderapi.domain.model;

import com.example.orderapi.domain.model.redis.Cart;
import com.example.orderapi.web.validation.form.cart.AddCartProductItemForm;
import com.example.orderapi.web.validation.form.cart.AddProductCartForm;
import com.example.orderapi.web.validation.form.productItem.AddProductItemForm;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.Audited;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Audited
@AuditOverride(forClass = BaseEntity.class)
public class ProductItem extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long sellerId;
    @Audited
    private String name;
    @Audited
    private Integer price;
    private Integer count;

    @ManyToOne //cascade Option? delete
    @JoinColumn(name="product_id")
    private Product product;

    public static ProductItem of(Long sellerId, AddProductItemForm form){
        return ProductItem.builder()
                .sellerId(sellerId)
                .name(form.getName())
                .price(form.getPrice())
                .count(form.getCount())
                .build();
    }

    public static ProductItem from(AddCartProductItemForm item) {
        return ProductItem.builder()
                .id(item.getProductId())
                .name(item.getName())
                .count(item.getCount())
                .price(item.getPrice())
                .build();
    }


}
