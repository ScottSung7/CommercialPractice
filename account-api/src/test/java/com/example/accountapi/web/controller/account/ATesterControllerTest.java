package com.example.accountapi.web.controller.account;

import com.example.accountapi.application.applications.AccountInfo.AccountInfoApplication;
import com.example.accountapi.application.service.signUp.customer.SignUpCustomerService;
import com.example.accountapi.application.service.signUp.seller.SignUpSellerService;
import com.example.accountapi.config.SpringSecurity.type.jwt.JWTUtil;
import com.example.accountapi.domain.model.Customer;
import com.example.accountapi.domain.model.Seller;
import com.example.accountapi.web.validation.exception.AccountException;
import com.example.accountapi.web.validation.form.customer.CustomerSignUpForm;
import com.example.accountapi.web.validation.form.seller.SellerSignUpForm;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static com.example.accountapi.web.controller.account.AccountAPIControllerProperties.ACCOUNT_COMMON_URL;
import static com.example.accountapi.web.controller.account.AccountAPIControllerProperties.CUSTOMER_SIGNUP;
import static com.example.accountapi.web.validation.exception.ErrorCode.NO_REGISTERED_TEST_CUSTOMER;
import static com.example.accountapi.web.validation.exception.ErrorCode.NO_REGISTERED_TEST_SELLER;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ATesterController.class)
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
@MockBean(JpaMetamodelMappingContext.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ATesterControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    private JWTUtil jwtUtil;
    @MockBean
    private SignUpCustomerService signUpCustomerService;
    @MockBean
    private SignUpSellerService signUpSellerService;
    @MockBean
    private AccountInfoApplication accountInfoApplication;

    private CustomerSignUpForm customerSignUpForm;
    private SellerSignUpForm sellerSignUpForm;

    @BeforeEach
    public void initEach(){
        customerSignUpForm = CustomerSignUpForm.testSignUpForm();

        sellerSignUpForm = SellerSignUpForm.testSignUpForm();
    }
    @Test
    @WithMockUser
    @Order(1)
    void jtokenCustomer() throws Exception {
        //given
        String email = "tester@test.com";
        Long customerId = 1L;
        String type = "CUSTOMER";
        Customer customer = Customer.builder().email(email).id(customerId).build();
        String requestedURL = "/test/create/customer";
        //when
        when(signUpCustomerService.signUp(any(CustomerSignUpForm.class))).thenReturn(customer);
        when(accountInfoApplication.findCustomer(email)).thenReturn(customer);
        when(jwtUtil.createJwt(email, type ,customerId, 60 * 60 * 10 * 1000L)).thenReturn("testJWT");
        ResultActions response = mockMvc.perform(post(requestedURL)
                .contentType(MediaType.APPLICATION_JSON)
                .with(csrf()));
        //then
        response.andExpect(status().isOk());
    }
    @Test
    @WithMockUser
    void jtokenLoginCustomer() throws Exception{
        //given
        String email = "tester@test.com";
        Long customerId = 1L;
        String type = "CUSTOMER";
        Customer customer = Customer.builder().email(email).id(customerId).build();
        String requestedURL = "/test/login/customer";
        //when
        when(accountInfoApplication.findCustomer(email)).thenReturn(customer);
        when(jwtUtil.createJwt(email, type ,customerId, 60 * 60 * 10 * 1000L)).thenReturn("testJWT");
        ResultActions response = mockMvc.perform(post(requestedURL)
                .contentType(MediaType.APPLICATION_JSON)
                .with(csrf()));
        //then
        response.andExpect(status().isOk());
    }
    @Mock
    Customer customerMock;
    @Test
    @WithMockUser
    void jtokenLoginCustomer_NO_REGISTERED_TEST_CUSTOMER() throws Exception{
        //given
        String email = "";
        String requestedURL = "/test/login/customer";
        //when
        when(accountInfoApplication.findCustomer(email)).thenReturn(null);
        ResultActions response = mockMvc.perform(post(requestedURL)
                .contentType(MediaType.APPLICATION_JSON)
                .with(csrf()));
        AccountException exception = assertThrows(AccountException.class, () -> {
            throw new AccountException(NO_REGISTERED_TEST_CUSTOMER);
        });
        //then
        assertEquals(NO_REGISTERED_TEST_CUSTOMER, exception.getErrorCode());
    }

    @Test
    @WithMockUser
    @Order(1)
    void jtokenSeller() throws Exception{
        //given
        String email = "tester@test.com";
        Long sellerId = 1L;
        String type = "SELLER";
        Seller seller = Seller.builder().email(email).id(sellerId).build();
        String requestedURL = "/test/create/seller";
        //when
        when(signUpSellerService.signUp(any(SellerSignUpForm.class))).thenReturn(seller);
        when(accountInfoApplication.findSeller(email)).thenReturn(seller);
        when(jwtUtil.createJwt(email, type ,sellerId, 60 * 60 * 10 * 1000L)).thenReturn("testJWT");
        ResultActions response = mockMvc.perform(post(requestedURL)
                .contentType(MediaType.APPLICATION_JSON)
                .with(csrf()));
        //then
        response.andExpect(status().isOk());
    }
    @Test
    @WithMockUser
    void jtokenLoginSeller() throws Exception{
        //given
        String email = "tester@test.com";
        Long sellerId = 1L;
        String type = "SELLER";
        Seller seller = Seller.builder().email(email).id(sellerId).build();
        String requestedURL = "/test/login/seller";
        //when
        when(accountInfoApplication.findSeller(email)).thenReturn(seller);
        when(jwtUtil.createJwt(email, type ,sellerId, 60 * 60 * 10 * 1000L)).thenReturn("testJWT");
        ResultActions response = mockMvc.perform(post(requestedURL)
                .contentType(MediaType.APPLICATION_JSON)
                .with(csrf()));
        //then
        response.andExpect(status().isOk());
    }
    @Mock
    Seller sellerMock;
    @Test
    @WithMockUser
    void jtokenLoginSeller_NO_REGISTERED_TEST_SELLER() throws Exception{
        //given
        String email = "";
        String requestedURL = "/test/login/seller";
        //when
        when(accountInfoApplication.findSeller(email)).thenReturn(null);
        ResultActions response = mockMvc.perform(post(requestedURL)
                .contentType(MediaType.APPLICATION_JSON)
                .with(csrf()));
        AccountException exception = assertThrows(AccountException.class, () -> {
            throw new AccountException(NO_REGISTERED_TEST_SELLER);
        });
        //then
        assertEquals(NO_REGISTERED_TEST_SELLER, exception.getErrorCode());
    }

}