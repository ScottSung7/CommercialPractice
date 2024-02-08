package com.example.item.web.controller;

import com.example.item.dto.ItemDTO;
import com.example.item.service.ItemRegisterService;
import com.example.item.web.form.ItemSaveForm;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
@Disabled
class ItemRegisterControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ItemRegisterService itemRegisterService;

    private ItemSaveForm saveForm;
    private ItemDTO itemDTO;

    @Autowired
    ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    public void initEach(){
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

        saveForm = new ItemSaveForm("testItem", 1000);
        itemDTO = new ItemDTO("testItem", 1000);

    }

    @Test
    @WithMockUser
    @DisplayName("Item 저장 확인 - Controller")
    void saveNewItem() throws Exception {

        //when
        ResultActions response = mockMvc.perform(post("/item/save")
                    .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                    .content(mapper.writeValueAsString(saveForm)));

        //then
         response.andExpect(status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.itemName").value("testItem"))
                   // .andExpect(MockMvcResultMatchers.jsonPath("$.itemPrice", CoreMatchers.is(itemDTO.getItemPrice())))
                    .andDo(MockMvcResultHandlers.print());
    }


}