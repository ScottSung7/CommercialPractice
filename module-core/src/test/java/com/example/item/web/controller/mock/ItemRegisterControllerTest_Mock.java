package com.example.item.web.controller.mock;

import com.example.item.domain.ItemEntity;
import com.example.item.dto.ItemDTO;
import com.example.item.service.ItemRegisterService;
import com.example.item.web.controller.ItemRegisterController;
import com.example.item.web.form.ItemSaveForm;
import com.example.login.config.SecurityConfiguration;
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
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = ItemRegisterController.class,
        excludeAutoConfiguration = SecurityAutoConfiguration.class,
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {SecurityConfiguration.class})
        })
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
@Disabled
class ItemRegisterControllerTest_Mock {


    @MockBean
    private ItemRegisterService itemRegisterService;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    ObjectMapper mapper;
    private ItemSaveForm saveForm;
    private ItemDTO itemDTO;


    @BeforeEach
    public void initEach(){
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

        saveForm = new ItemSaveForm("testItem", 1000);
        itemDTO = new ItemDTO("testItem", 1000);

    }

    @Test
  //  @WithMockUser
    @DisplayName("Item 저장 확인 - Controller")
    void saveNewItem() throws Exception {
        //given
       when(itemRegisterService.saveNewItem(any(ItemEntity.class))).thenReturn(itemDTO);

        //when
        ResultActions response = mockMvc.perform(post("/item/save")
                    .contentType(MediaType.APPLICATION_JSON)
                   // .with(csrf())
                    .content(mapper.writeValueAsString(saveForm)));

        //then
         response.andExpect(status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.itemName").value("testItem"))
                   // .andExpect(MockMvcResultMatchers.jsonPath("$.itemPrice", CoreMatchers.is(itemDTO.getItemPrice())))
                    .andDo(MockMvcResultHandlers.print());
    }


}