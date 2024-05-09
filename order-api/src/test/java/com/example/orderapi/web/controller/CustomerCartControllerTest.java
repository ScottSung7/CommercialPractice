package com.example.orderapi.web.controller;

import com.example.orderapi.application.application.CartApplication;
import com.example.orderapi.config.common.SpringSecurity.id.UserPrincipalDetails;
import com.example.orderapi.config.common.validation.LoginCheckMSA;
import com.example.orderapi.web.validation.form.cart.AddCartProductItemForm;
import com.example.orderapi.web.validation.form.cart.AddProductCartForm;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
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
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(CustomerCartController.class)
@ExtendWith(MockitoExtension.class)
@MockBean(JpaMetamodelMappingContext.class)
class CustomerCartControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    private CartApplication cartApplication;

    private static MockedStatic<LoginCheckMSA> loginCheckMSA;
    @BeforeAll
    public static void beforeAll() {
        loginCheckMSA = mockStatic(LoginCheckMSA.class);
    }

    @AfterAll
    public static void afterAll() {
        loginCheckMSA.close();
    }

    @Mock
    AddProductCartForm addProductCartForm;
    @Mock
    AddCartProductItemForm addCartProductItemForm;
    @BeforeEach
    private void before(){
        //given - add Cart
        addProductCartForm = mock(AddProductCartForm.class, withSettings().lenient());
        addCartProductItemForm = mock(AddCartProductItemForm.class, withSettings().lenient());

        given(addProductCartForm.getId()).willReturn(1L);
        given(addProductCartForm.getName()).willReturn("testProduct");
        given(addProductCartForm.getDescription()).willReturn("testDescription");
        given(addProductCartForm.getAddCartProductItemForms()).willReturn(List.of(addCartProductItemForm));
        given(addCartProductItemForm.getProductId()).willReturn(1L);
        given(addCartProductItemForm.getName()).willReturn("testProductItem");
        given(addCartProductItemForm.getPrice()).willReturn(1000);
        given(addCartProductItemForm.getCount()).willReturn(1);
    }


    @Test
    @WithMockUser
    void addCart() throws Exception {
        Long customerId = 1L;

        UserPrincipalDetails customer = mock(UserPrincipalDetails.class);
        given(customer.getId()).willReturn(customerId);
        given(LoginCheckMSA.customerCheck(any(Authentication.class))).willReturn(customer);

        String requestURL = "/customer/cart";
        ResultActions response = mockMvc.perform(post(requestURL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(addProductCartForm))
                .with(csrf()));

        //then
        response.andExpect(status().isOk());


    }

    @Test
    @WithMockUser
    void showCart() throws Exception {
        Long customerId = 1L;

        UserPrincipalDetails customer = mock(UserPrincipalDetails.class);
        given(customer.getId()).willReturn(customerId);
        given(LoginCheckMSA.customerCheck(any(Authentication.class))).willReturn(customer);

        String requestURL = "/customer/cart";
        ResultActions response = mockMvc.perform(get(requestURL)
                .with(csrf()));
        //then
        response.andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void clearCart() throws Exception {
        Long customerId = 1L;

        UserPrincipalDetails customer = mock(UserPrincipalDetails.class);
        given(customer.getId()).willReturn(customerId);
        given(LoginCheckMSA.customerCheck(any(Authentication.class))).willReturn(customer);

        String requestURL = "/customer/cart";
        ResultActions response = mockMvc.perform(delete(requestURL)
                .with(csrf()));

        //then
        response.andExpect(status().isOk());

    }

}