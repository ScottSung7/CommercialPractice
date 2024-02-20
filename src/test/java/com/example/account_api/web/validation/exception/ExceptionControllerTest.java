package com.example.account_api.web.validation.exception;

import com.example.account_api.web.controller.SignUpController;
import com.example.config.CorsConfig;
import com.example.config.SecurityConfiguration;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
@Import({SecurityConfiguration.class, CorsConfig.class}) //SpringSecurity때문에 추가(csrf().disabled)
class ExceptionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SignUpController signUpController;

    @Test
    @DisplayName("Exception 테스트")
    public void test1() throws Exception{

        when(signUpController.hi()).thenThrow(new AccountException(ErrorCode.UNKNOWN_ERROR));

        ResultActions response = mockMvc.perform(post("/accounts/hi")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(status().isBadRequest()) //처음에만.
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorCode").value("UNKNOWN_ERROR"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("알 수 없는 에러가 발생하였습니다."))
                .andDo(MockMvcResultHandlers.print());

    }




}