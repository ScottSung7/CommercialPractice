package com.example.item.service;

import com.example.item.domain.ItemEntity;
import com.example.item.dto.ItemDTO;
import com.example.item.repository.ItemRegisterRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@Disabled
class ItemRegisterServiceImplTest {


    @Autowired
    private ItemRegisterService itemRegisterService;

    private ItemEntity itemEntityFromRequest;
    private ItemEntity itemEntityReturned;

    @BeforeEach
    public void initBefore(){
        itemEntityFromRequest = ItemEntity.builder()
                                    .itemName("testItemEntity")
                                    .itemPrice(1000)
                                    .build();
    }

    @Test
    void ItemRegisterService_saveNewItem_DTOReturned_isNotNull() {
        ItemDTO itemDTO = itemRegisterService.saveNewItem(itemEntityFromRequest);
        System.out.println(itemDTO);
        assertThat(itemDTO).isNotNull();
    }

}