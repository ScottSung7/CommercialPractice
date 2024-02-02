package com.example.item.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import com.example.item.web.form.ItemSaveForm;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itemId;
    private String itemName;
    private int itemPrice;

    public static ItemEntity saveFormToItem(ItemSaveForm form){
        return ItemEntity.builder()
                .itemName(form.getItemName())
                .itemPrice(form.getItemPrice())
                .build();
    }







}
