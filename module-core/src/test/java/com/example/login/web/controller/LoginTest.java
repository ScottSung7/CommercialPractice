package com.example.login.web.controller;


import com.example.login.config.Jwt.JwtProperties;
import com.example.login.domain.Users;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;


import java.net.URI;
import java.net.URISyntaxException;

import static java.lang.String.format;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Disabled
public class LoginTest {


    @Autowired
    private  MockMvc mockMvc;
    @Autowired
    private UserRegisterController controller;
    @Autowired
    private ObjectMapper mapper;

    private final String loginURL = "/login";
   private final String jwtHeaderName = JwtProperties.HEADER_STRING;
    private final String testerUsername = "tester";
    private final String testerPassword = "1234";

    @BeforeEach
    public void initEach(){
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

        Users testUser = Users.builder().username(testerUsername).password(testerPassword).build();
        controller.joinUser(testUser);
    }

    @Test
    @Transactional
    @DisplayName("1. jwt로 로그인 성공")
    public void login_success() throws Exception{

        Users userLogin = Users.builder().username(testerUsername).password(testerPassword).build();

        ResultActions response = mockMvc.perform(post(loginURL)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(mapper.writeValueAsString(userLogin))
                                    .accept(MediaType.APPLICATION_JSON));


        response.andExpect(header().exists(jwtHeaderName))
                .andExpect(status().isOk()); // 200 : OK

    }

    @Test
    @Transactional
    @DisplayName("2. 비밀번호 오류로 로그인 실패")
    public void login_unsuccessful() throws Exception{

        Users userLogin = Users.builder().username(testerUsername).password("12345").build();

        ResultActions response = mockMvc.perform(post(loginURL)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(mapper.writeValueAsString(userLogin))
                                    .accept(MediaType.APPLICATION_JSON));


        response.andExpect(status().isUnauthorized()); //401 : Unauthorized
    }


}

