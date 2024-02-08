package com.example.item.web.controller;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.item.domain.ItemEntity;
import com.example.item.dto.ItemDTO;
import com.example.item.service.ItemRegisterService;
import com.example.item.web.form.ItemSaveForm;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@Disabled
class ItemRegisterControllerTest_original {

    @InjectMocks
    private ItemRegisterController itemRegisterController;
    @Mock
    private ItemRegisterService itemRegisterService;
    private MockMvc mockMvc;

    private ItemSaveForm saveForm;
    private ItemDTO itemDTO;
    ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    public void initEach(){
        mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

        mockMvc = MockMvcBuilders.standaloneSetup(itemRegisterController).build();
        saveForm = new ItemSaveForm("testItem", 1000);
        itemDTO = new ItemDTO("testItem", 1000);

    }

    @Test
    @DisplayName("Item 저장 확인 - Controller")
    void saveNewItem() throws Exception {
        //given
        when(itemRegisterService.saveNewItem(any(ItemEntity.class))).thenReturn(itemDTO);

        //when
        ResultActions response = mockMvc.perform(post("/item/save")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsString(saveForm)));

        //then
         response.andExpect(status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.itemName").value("testItem"))
                   // .andExpect(MockMvcResultMatchers.jsonPath("$.itemPrice", CoreMatchers.is(itemDTO.getItemPrice())))
                    .andDo(MockMvcResultHandlers.print());
    }


}