package com.example.account_api.web.controller.mock;

import com.example.account_api.application.SignUpApplication;
import com.example.account_api.web.controller.account.SignUpController;
import com.example.account_api.web.validation.form.customer.SignUpCustomerForm;
import com.example.config.CorsConfig;
import com.example.config.SecurityConfiguration_Session;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.time.LocalDate;

import static com.example.account_api.web.controller.account.AccountAPIControllerProperties.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SignUpController.class)
@AutoConfigureMockMvc
@Import({SecurityConfiguration_Session.class, CorsConfig.class}) //SpringSecurity때문에 추가(csrf().disabled)
class SignUpControllerTest_mock {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    private SignUpApplication signUpApplication;

    private SignUpCustomerForm signUpCustomerForm;

    @BeforeEach
    public void initEach(){
        signUpCustomerForm = SignUpCustomerForm.builder()
                .email("tester's email")
                .name("tester")
                .phone("1234")
                .password("1122")
                .birth(LocalDate.now())
                .build();
    }

    @Test
    @DisplayName("Mock: 고객 회원가입")
    @WithMockUser
    void customerSignUp_URL_Test() throws Exception {
        when(signUpApplication.customerSignUp(any(SignUpCustomerForm.class))).thenReturn("회원가입 성공");

        String requestedURL = ACCOUNT_COMMON_URL + CUSTOMER_SIGNUP;
        ResultActions response = mockMvc.perform(post(requestedURL)
                .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                .content(objectMapper.writeValueAsString(signUpCustomerForm)));

        response.andExpect(status().isOk());
     }

    @Test
    void hi_test() throws Exception{
        ResultActions response = mockMvc.perform(post(ACCOUNT_COMMON_URL + "/hi")
                .contentType(MediaType.APPLICATION_JSON));

        response.andDo(MockMvcResultHandlers.print());

    }

    @Test
    void customerUpdate() {
    }

    @Test
    @DisplayName("판매자 회원가입")
    void sellerSignUp() {
    }

    @Test
    void sellerUpdate() {
    }
}