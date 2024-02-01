package com.example.item.repository;

import com.example.item.domain.ItemEntity;
import org.springframework.stereotype.Repository;

@Repository
public class ItemRegisterRepositoryMybatis implements ItemRegisterRepository{
    @Override
    public ItemEntity save(ItemEntity itemEntity) {
        return null;
    }

    @Override
    public ItemEntity update() {
        return null;
    }

    @Override
    public ItemEntity findByItemName() {
        return null;
    }
}
