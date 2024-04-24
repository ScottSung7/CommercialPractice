package com.example.accountapi.web.controller.account;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LogInController.class)
@AutoConfigureWebMvc
@MockBean(JpaMetamodelMappingContext.class)
@ExtendWith(MockitoExtension.class)
class LogInControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser
    void loginCustomer() throws Exception {
        //given
        String requestedUrl = "/accounts/customer/login";
        //when
        ResultActions response = mockMvc.perform(get(requestedUrl)
                                    .with(csrf()));
        //then
        response.andExpect(status().isOk());

    }

    @Test
    @WithMockUser
    void loginSeller() throws Exception{
        //given
        String requestedUrl = "/accounts/seller/login";
        //when
        ResultActions response = mockMvc.perform(get(requestedUrl)
                        .with(csrf()));
        //then
        response.andExpect(status().isOk());
    }
}