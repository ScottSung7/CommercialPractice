package com.example.item.service.mock;

import com.example.item.domain.ItemEntity;
import com.example.item.dto.ItemDTO;
import com.example.item.repository.ItemRegisterRepository;
import com.example.item.service.ItemRegisterService;
import com.example.item.service.ItemRegisterServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Disabled
class ItemRegisterServiceImplTest_Mock {

    @InjectMocks
    private ItemRegisterService itemRegisterService;

    @Mock
    private ItemRegisterRepository itemRegisterRepository;

    private ItemEntity itemEntityFromRequest;
    private ItemEntity itemEntityReturned;

    @BeforeEach
    public void initBefore(){
       // itemRegisterService = new ItemRegisterServiceImpl(itemRegisterRepository);

        itemEntityFromRequest = ItemEntity.builder()
                                    .itemName("testItemEntity")
                                    .itemPrice(1000)
                                    .build();
        itemEntityReturned = ItemEntity.builder()
                                    .itemId(1L)
                                    .itemName("testItemEntity")
                                    .itemPrice(1000).build();
    }

    @Test
    void ItemRegisterService_saveNewItem_DTOReturned_isNotNull() {
        when(itemRegisterRepository.save(any(ItemEntity.class))).thenReturn(itemEntityReturned);

        ItemDTO itemDTO = itemRegisterService.saveNewItem(itemEntityFromRequest);

        assertThat(itemDTO).isNotNull();
    }
}