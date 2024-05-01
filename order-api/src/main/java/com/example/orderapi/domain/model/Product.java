package com.example.orderapi.domain.model;

import com.example.orderapi.web.validation.exception.OrderErrorCode;
import com.example.orderapi.web.validation.exception.OrderException;
import com.example.orderapi.web.validation.form.cart.AddProductCartForm;
import com.example.orderapi.web.validation.form.product.AddProductForm;
import com.example.orderapi.web.validation.form.product.UpdateProductForm;
import com.example.orderapi.web.validation.form.productItem.AddExtraProductItemForm;
import com.example.orderapi.web.validation.form.productItem.AddProductItemForm;
import com.example.orderapi.web.validation.form.productItem.UpdateProductItemForm;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.Audited;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter(AccessLevel.PRIVATE)
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Audited //변할때마다 변화 여기 저장
@AuditOverride(forClass = BaseEntity.class)
public class Product extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long sellerId;
    private String name;
    private String description; //이미지등 추가 필요

    @OneToMany(mappedBy="product", cascade = CascadeType.ALL)
    @Builder.Default
    private List<ProductItem> productItems = new ArrayList<>();

    public static Product of(Long sellerId, AddProductForm addProductForm){
         Product product = Product.builder()
                 .sellerId(sellerId)
                 .name(addProductForm.getName())
                 .description(addProductForm.getDescription())
                 .build();

         addProductForm.getAddProductItemForms().stream()
                 .forEach(addProductItemForm -> addProductItem(product, sellerId, addProductItemForm));

         return product;
    }

    public static ProductItem addProductItem(Product product, Long sellerId, AddProductItemForm form) {
        if(product.getProductItems() != null && product.getProductItems().stream()
                .anyMatch(item -> item.getName().equals(form.getName()))){
            throw new OrderException(OrderErrorCode.SAME_ITEM_NAME);
        }
        ProductItem productItem = ProductItem.of(sellerId, form);
        productItem.addProduct(product);

        product.getProductItems().add(productItem);

        return productItem;
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

    //Business Method
    public Product updateProduct(UpdateProductForm form, Product product) {
        product.setName(form.getName());
        product.setDescription(form.getDescription());

        System.out.println("in?");
        for(UpdateProductItemForm itemForm : form.getUpdateProductItemForms()){
            ProductItem item = product.getProductItems().stream()
                    .filter(pi -> pi.getId().equals(itemForm.getItemId()))
                    .findFirst().orElseThrow(() -> new OrderException(OrderErrorCode.NOT_FOUND_ITEM));
            item.updateFromProduct(item, itemForm);
        }
        return product;
    }
//    public Product addProductItem(Product product, Long sellerId, AddExtraProductItemForm form) {
//
//        if(product.getProductItems().stream()
//                .anyMatch(item -> item.getName().equals(form.getName()))){
//            throw new OrderException(OrderErrorCode.SAME_ITEM_NAME);
//        }
//        ProductItem productItem = ProductItem.of(sellerId, form);
//        productItem.addProduct(product);
//
//        product.getProductItems().add(productItem);
//        return product;
//
//    }
}
