package com.example.orderapi.web.controller;

import com.example.orderapi.application.service.ProductItemService;
import com.example.orderapi.application.service.ProductSearchService;
import com.example.orderapi.application.service.ProductService;
import com.example.orderapi.config.common.SpringSecurity.id.UserPrincipalDetails;
import com.example.orderapi.config.common.validation.LoginCheckMSA;
import com.example.orderapi.domain.model.Product;
import com.example.orderapi.web.validation.form.productItem.AddExtraProductItemForm;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SearchController.class)
@ExtendWith(MockitoExtension.class)
@MockBean(JpaMetamodelMappingContext.class)
class SearchControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    private ProductSearchService productSearchService;



    @Test
    @WithMockUser
    void searchByName() throws Exception {
        //given
        String name = "test";

        // when
        Product product = mock(Product.class);
        when(productSearchService.searchByName(name)).thenReturn(List.of(product));

        String requestURL = "/search/product";
        ResultActions response = mockMvc.perform(get(requestURL)
                .param("name", name)
                .with(csrf()));
        // then
        response.andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void getDetail() throws Exception {
        //given
        Long productId = 1L;

        // when
        Product product = mock(Product.class);
        when(productSearchService.getByProductId(productId)).thenReturn(product);

        String requestURL = "/search/product/detail";
        ResultActions response = mockMvc.perform(get(requestURL)
                .param("productId", String.valueOf(productId))
                .with(csrf()));
        // then
        response.andExpect(status().isOk());
    }
}