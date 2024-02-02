package com.example.item.repository;

import com.example.item.domain.ItemEntity;
import org.springframework.stereotype.Repository;


@Repository
public class ItemRegisterRepository_Mybatis implements ItemRegisterRepository{
    @Override
    public ItemEntity save(ItemEntity itemEntity) {
        return null;
    }


    public ItemEntity update() {
        return null;
    }


    public ItemEntity findByItemName() {
        return null;
    }
}
