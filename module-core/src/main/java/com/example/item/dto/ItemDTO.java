package com.example.item.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import com.example.item.domain.ItemEntity;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ItemDTO {

    private String itemName;

    private int itemPrice;

    public static ItemDTO itemEntityToItemDTO(ItemEntity itemEntity){
        return ItemDTO.builder()
                .itemName(itemEntity.getItemName())
                .itemPrice(itemEntity.getItemPrice())
                .build();
    }

}
