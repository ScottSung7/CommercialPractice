package com.example.accountapi.web.validation.exception;

import com.example.accountapi.application.service.balance.CustomerBalanceService;
import com.example.accountapi.web.controller.account.AccountInfoController;
import com.example.accountapi.web.controller.account.SignUpController;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


@WebMvcTest
@AutoConfigureMockMvc
@MockBean(JpaMetamodelMappingContext.class)
@ExtendWith(MockitoExtension.class)
@Disabled
class ExceptionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SignUpController signUpController;

    @MockBean
    private AccountInfoController accountInfoController;

    @MockBean
    private CustomerBalanceService customerBalanceService;

    @Test
    @DisplayName("Exception 테스트")
    @WithMockUser
    @Disabled
    public void test1() throws Exception{
//
//        when(signUpController.hi()).thenThrow(new AccountException(ErrorCode.UNKNOWN_ERROR));
//
//        ResultActions response = mockMvc.perform(post("/accounts/hi3")
//                .with(csrf())
//                .contentType(MediaType.APPLICATION_JSON));
//
//        response.andExpect(status().isBadRequest()) //처음에만.
//                .andExpect(MockMvcResultMatchers.jsonPath("$.errorCode").value("UNKNOWN_ERROR"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("알 수 없는 에러가 발생하였습니다."))
//                .andDo(MockMvcResultHandlers.print());

    }




}