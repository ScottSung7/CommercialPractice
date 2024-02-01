package com.example.item.web.controller;

import lombok.RequiredArgsConstructor;
import com.example.item.domain.ItemEntity;
import com.example.item.dto.ItemDTO;
import com.example.item.service.ItemRegisterService;
import com.example.item.web.form.ItemSaveForm;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ItemRegisterController {

    private final ItemRegisterService itemRegisterService;

    @PostMapping("/item/save")
    public ItemDTO saveNewItem(@RequestBody ItemSaveForm form){

        ItemDTO dto =itemRegisterService.saveNewItem(ItemEntity.saveFormToItem(form));
        return dto;
    }

}
