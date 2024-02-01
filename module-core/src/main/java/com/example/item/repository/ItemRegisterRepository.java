package com.example.item.repository;

import com.example.item.domain.ItemEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRegisterRepository {

    public ItemEntity save(ItemEntity itemEntity);
    public ItemEntity update();
    public ItemEntity findByItemName();


}
