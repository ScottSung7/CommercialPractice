package com.example.item.service;

import com.example.item.domain.ItemEntity;
import com.example.item.dto.ItemDTO;
import org.springframework.stereotype.Service;

@Service
public interface ItemRegisterService {

    public ItemDTO saveNewItem(ItemEntity itemEntity);

}
