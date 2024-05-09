package com.example.accountapi.web.controller.account;

import com.example.accountapi.application.applications.AccountInfo.AccountInfoApplication;
import com.example.accountapi.config.SpringSecurity.id.customer.CustomerPrincipalDetails;
import com.example.accountapi.config.SpringSecurity.id.seller.SellerPrincipalDetails;
import com.example.accountapi.domain.model.Customer;
import com.example.accountapi.domain.model.Seller;
import com.example.accountapi.config.SpringSecurity.LoginCheck;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
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

import static com.example.accountapi.web.controller.account.AccountAPIControllerProperties.ACCOUNT_COMMON_URL;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(AccountInfoController.class)
@ExtendWith(MockitoExtension.class)
@MockBean(JpaMetamodelMappingContext.class)
class AccountInfoControllerTest {

    @Autowired
    private MockMvc movkcMvc;
    @Autowired
    private ObjectMapper objectMapper;

    private static MockedStatic<LoginCheck> loginCheck;

    @BeforeAll
    public static void beforeAll() {
        loginCheck = mockStatic(LoginCheck.class);
    }

    @AfterAll
    public static void afterAll() {
        loginCheck.close();
    }

    @MockBean
    private AccountInfoApplication accountInfoApplication;



    @Test
    @WithMockUser
    void getCustomerInfo() throws Exception {
        //given
        Customer customer = Customer.builder().email("tester@test.com").build();
        CustomerPrincipalDetails customerPrincipalDetails = mock(CustomerPrincipalDetails.class);
        when(customerPrincipalDetails.getEmail()).thenReturn("tester@test.com");

        //when
        when(LoginCheck.customerCheck(any(Authentication.class))).thenReturn(customerPrincipalDetails);
        when(accountInfoApplication.findCustomer(anyString())).thenReturn(customer);
        ResultActions response = movkcMvc.perform(post(ACCOUNT_COMMON_URL + "/customer")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON));
        //then
        response.andExpect(status().isOk());
    }
    @Test
    @WithMockUser
    void searchCustomerInfo() throws Exception {
        //given
        Customer customer = mock(Customer.class);
        when(accountInfoApplication.findCustomer(anyString())).thenReturn(customer);

        //when
        ResultActions response = movkcMvc.perform(post(ACCOUNT_COMMON_URL + "/customer/info")
                        .param("email", "tester@test.com/*")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON));
        //then
        response.andExpect(status().isOk());
    }


    @Test
    @WithMockUser
    void getSellerInfo() throws Exception{
        //given
        Seller seller = Seller.builder().email("tester@test.com").build();
        SellerPrincipalDetails sellerPrincipalDetails = mock(SellerPrincipalDetails.class);
        when(sellerPrincipalDetails.getEmail()).thenReturn("tester@test.com");

        //when
        when(LoginCheck.sellerCheck(any(Authentication.class))).thenReturn(sellerPrincipalDetails);
        when(accountInfoApplication.findSeller(anyString())).thenReturn(seller);
        ResultActions response = movkcMvc.perform(post(ACCOUNT_COMMON_URL + "/seller")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON));
        //then
        response.andExpect(status().isOk());
    }
    @Test
    @WithMockUser
    void searchSellerInfo() throws Exception {
        //given
        Seller seller = mock(Seller.class);
        when(accountInfoApplication.findSeller(anyString())).thenReturn(seller);

        //when
        ResultActions response = movkcMvc.perform(post(ACCOUNT_COMMON_URL + "/seller/info")
                .param("email", "tester@test.com/*")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON));
        //then
        response.andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void searchUserInfo() throws Exception {
        //given
        Customer customer = mock(Customer.class);
        Seller seller = mock(Seller.class);
        when(accountInfoApplication.findCustomer(anyString())).thenReturn(customer);
        when(accountInfoApplication.findSeller(anyString())).thenReturn(seller);


        //when
        ResultActions response = movkcMvc.perform(post(ACCOUNT_COMMON_URL + "/user/info")
                .param("email", "tester@test.com/*")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON));
        //then
        response.andExpect(status().isOk());
    }
}