package com.example.item.service;

import lombok.RequiredArgsConstructor;
import com.example.item.domain.ItemEntity;
import com.example.item.dto.ItemDTO;
import com.example.item.repository.ItemRegisterRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItemRegisterServiceImpl implements ItemRegisterService{

    private final ItemRegisterRepository itemRegisterRepository;
    @Override
    public ItemDTO saveNewItem(ItemEntity itemEntity) {
        return ItemDTO.itemEntityToItemDTO(itemRegisterRepository.save(itemEntity));
    }
}
