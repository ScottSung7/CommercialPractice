package com.example.orderapi.domain.model;

import com.example.orderapi.web.validation.form.cart.AddCartProductItemForm;
import com.example.orderapi.web.validation.form.productItem.AddProductItemForm;
import com.example.orderapi.web.validation.form.productItem.UpdateProductItemForm;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.Audited;

@Entity
@Getter
@Setter(AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    @ManyToOne(fetch=FetchType.LAZY) //cascade Option? delete
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

    //Business Logic
    public ProductItem updateProductItem(UpdateProductItemForm form) {
        System.out.println("Updating");
        System.out.println(form.getCount());
        this.name = form.getName();
        this.price = form.getPrice();
        this.count = form.getCount();
        return this;
    }


    public void changeCount(int changedCount) {
        this.count = changedCount;
    }
    public void changePrice(int changedPrice) {
        this.price = changedPrice;
    }
    public void addProduct(Product product) {
        this.product = product;
    }
}
