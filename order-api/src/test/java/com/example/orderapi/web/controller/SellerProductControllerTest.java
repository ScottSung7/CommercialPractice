package com.example.orderapi.web.controller;

import com.example.orderapi.application.application.CartApplication;
import com.example.orderapi.application.service.ProductItemService;
import com.example.orderapi.application.service.ProductService;
import com.example.orderapi.config.common.SpringSecurity.id.UserPrincipalDetails;
import com.example.orderapi.config.common.validation.LoginCheckMSA;
import com.example.orderapi.domain.model.Product;
import com.example.orderapi.domain.model.ProductItem;
import com.example.orderapi.web.validation.form.cart.AddCartProductItemForm;
import com.example.orderapi.web.validation.form.product.AddProductForm;
import com.example.orderapi.web.validation.form.product.UpdateProductForm;
import com.example.orderapi.web.validation.form.productItem.AddExtraProductItemForm;
import com.example.orderapi.web.validation.form.productItem.AddProductItemForm;
import com.example.orderapi.web.validation.form.productItem.UpdateProductItemForm;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.With;
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

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.withSettings;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SellerProductController.class)
@ExtendWith(MockitoExtension.class)
@MockBean(JpaMetamodelMappingContext.class)
class SellerProductControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    private ProductService productService;
    @MockBean
    private ProductItemService productItemService;

    private static MockedStatic<LoginCheckMSA> loginCheckMSA;
    @BeforeAll
    public static void beforeAll() {
        loginCheckMSA = mockStatic(LoginCheckMSA.class);
    }
    @AfterAll
    public static void afterAll() {
        loginCheckMSA.close();
    }

    private Long sellerId = 1L;

    @Test
    @WithMockUser
    void addProduct() throws Exception {

        // given - login
        UserPrincipalDetails seller = mock(UserPrincipalDetails.class);
        given(seller.getId()).willReturn(sellerId);
        given(LoginCheckMSA.sellerCheck(any(Authentication.class))).willReturn(seller);
        //given - form
        AddProductForm addProductForm = mock(AddProductForm.class, withSettings().lenient());
        AddProductItemForm addProductItemForm = mock(AddProductItemForm.class, withSettings().lenient());

        given(addProductForm.getName()).willReturn("testProduct");
        given(addProductForm.getDescription()).willReturn("testDescription");

        given(addProductItemForm.getName()).willReturn("testProductItem");
        given(addProductItemForm.getPrice()).willReturn(1000);
        given(addProductItemForm.getCount()).willReturn(10);

        List<AddProductItemForm> itemForms = new ArrayList<>();
        itemForms.add(addProductItemForm);
        given(addProductForm.getAddProductItemForms()).willReturn(itemForms);

        // when
        Product product = mock(Product.class);
        when(productService.addProduct(anyLong(), any(AddProductForm.class))).thenReturn(product);
        String requestURL = "/seller/product";
        ResultActions response = mockMvc.perform(post(requestURL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(addProductForm))
                .with(csrf()));
        // then
        response.andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void addProductItem() throws Exception {
        //given
        UserPrincipalDetails seller = mock(UserPrincipalDetails.class);
        given(seller.getId()).willReturn(sellerId);
        given(LoginCheckMSA.sellerCheck(any(Authentication.class))).willReturn(seller);

        AddExtraProductItemForm addExtraProductItemForm = mock(AddExtraProductItemForm.class);
        given(addExtraProductItemForm.getName()).willReturn("testExtraProductItem");
        given(addExtraProductItemForm.getPrice()).willReturn(1000);
        given(addExtraProductItemForm.getCount()).willReturn(10);

        // when
        Product product = mock(Product.class);
        when(productItemService.addProductItem(anyLong(), any(AddExtraProductItemForm.class))).thenReturn(product);
        String requestURL = "/seller/product/item";
        ResultActions response = mockMvc.perform(post(requestURL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(addExtraProductItemForm))
                .with(csrf()));
        // then
        response.andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void updateProduct() throws Exception {
        //given
        UserPrincipalDetails seller = mock(UserPrincipalDetails.class);
        given(seller.getId()).willReturn(sellerId);
        given(LoginCheckMSA.sellerCheck(any(Authentication.class))).willReturn(seller);

        UpdateProductForm updateProductForm = mock(UpdateProductForm.class);
        UpdateProductItemForm updateProductItemForm = mock(UpdateProductItemForm.class);
        given(updateProductForm.getId()).willReturn(1L);
        given(updateProductForm.getName()).willReturn("updatedProduct");
        given(updateProductForm.getDescription()).willReturn("updatedDescription");

        given(updateProductItemForm.getProductId()).willReturn(1L);
        given(updateProductItemForm.getName()).willReturn("updatedProductItem");
        given(updateProductItemForm.getPrice()).willReturn(2000);
        given(updateProductItemForm.getCount()).willReturn(20);
        given(updateProductForm.getUpdateProductItemForms()).willReturn(List.of(updateProductItemForm));

        // when
        Product product = mock(Product.class);
        when(productService.updateProduct(anyLong(), any(UpdateProductForm.class))).thenReturn(product);
        String requestURL = "/seller/product";
        ResultActions response = mockMvc.perform(put(requestURL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateProductForm))
                .with(csrf()));
        // then
        response.andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void updateProductItem() throws Exception {
        //given
        UserPrincipalDetails seller = mock(UserPrincipalDetails.class);
        given(seller.getId()).willReturn(sellerId);
        given(LoginCheckMSA.sellerCheck(any(Authentication.class))).willReturn(seller);

        UpdateProductItemForm updateProductItemForm = mock(UpdateProductItemForm.class);
        given(updateProductItemForm.getProductId()).willReturn(1L);
        given(updateProductItemForm.getItemId()).willReturn(2L);
        given(updateProductItemForm.getName()).willReturn("updatedProductItem");
        given(updateProductItemForm.getPrice()).willReturn(2000);
        given(updateProductItemForm.getCount()).willReturn(20);


        // when
        ProductItem productItem = mock(ProductItem.class);
        when(productItemService.updateProductItem(anyLong(), any(UpdateProductItemForm.class))).thenReturn(productItem);
        String requestURL = "/seller/product/item";
        ResultActions response = mockMvc.perform(put(requestURL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateProductItemForm))
                .with(csrf()));
        // then
        response.andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void deleteProduct() throws Exception {
        //given
        UserPrincipalDetails seller = mock(UserPrincipalDetails.class);
        given(seller.getId()).willReturn(sellerId);
        given(LoginCheckMSA.sellerCheck(any(Authentication.class))).willReturn(seller);

        Long productId = 1L;

        // when
        doNothing().when(productService).deleteProduct(anyLong(), anyLong());
        String requestURL = "/seller/product";
        ResultActions response = mockMvc.perform(delete(requestURL)
                .param("productId", String.valueOf(productId))
                .with(csrf()));
        // then
        response.andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void deleteProductItem() throws Exception {
        //given
        UserPrincipalDetails seller = mock(UserPrincipalDetails.class);
        given(seller.getId()).willReturn(sellerId);
        given(LoginCheckMSA.sellerCheck(any(Authentication.class))).willReturn(seller);

        Long itemId = 1L;

        // when
        doNothing().when(productItemService).deleteProductItem(anyLong(), anyLong());

        String requestURL = "/seller/product/item";
        ResultActions response = mockMvc.perform(delete(requestURL)
                .param("itemId", String.valueOf(itemId))
                .with(csrf()));
        // then
        response.andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void getMyProducts() throws Exception {
        UserPrincipalDetails seller = mock(UserPrincipalDetails.class);
        given(seller.getId()).willReturn(sellerId);
        given(LoginCheckMSA.sellerCheck(any(Authentication.class))).willReturn(seller);

        // when
        Product product = mock(Product.class);
        when(productService.getMyProducts(anyLong())).thenReturn(List.of(product));
        String requestURL = "/seller/product/myproducts";
        ResultActions response = mockMvc.perform(post(requestURL)
                .with(csrf()));
        // then
        response.andExpect(status().isOk());
    }
}