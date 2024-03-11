package com.example.accountapi.web.controller.mock;


import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
@Disabled
class LogInControllerTest_SpringSecurity {


    @Autowired
    MockMvc mockMvc;

    @Test
    public void home() throws Exception{
        mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void test_user() throws Exception{
        mockMvc.perform(get("/test")
                        .with(user("hi")))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void test_not_user() throws Exception{
        mockMvc.perform(get("/test"))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }
}