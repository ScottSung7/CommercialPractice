package com.example.accountapi.web.controller.account;

import com.example.accountapi.application.applications.signUp.SignUpApplication;
import com.example.accountapi.application.tools.crpto.AESCryptoUtil;
import com.example.accountapi.config.SpringSecurity.id.customer.CustomerPrincipalDetails;
import com.example.accountapi.config.SpringSecurity.id.seller.SellerPrincipalDetails;
import com.example.accountapi.domain.model.Customer;
import com.example.accountapi.domain.model.Seller;
import com.example.accountapi.config.SpringSecurity.LoginCheck;
import com.example.accountapi.web.validation.form.customer.CustomerSignUpForm;
import com.example.accountapi.web.validation.form.customer.CustomerUpdateForm;
import com.example.accountapi.web.validation.form.seller.SellerSignUpForm;
import com.example.accountapi.web.validation.form.seller.SellerUpdateForm;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;

import static com.example.accountapi.web.controller.account.AccountAPIControllerProperties.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SignUpController.class)
@ExtendWith(MockitoExtension.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureMockMvc
class SignUpControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    private SignUpApplication signUpApplication;

    private CustomerSignUpForm customerSignUpForm;
    private SellerSignUpForm sellerSignUpForm;

    private static MockedStatic<LoginCheck> loginCheck;

    @BeforeEach
    public void initEach() {
        customerSignUpForm = CustomerSignUpForm.testSignUpForm();
        sellerSignUpForm = SellerSignUpForm.testSignUpForm();
    }

    @BeforeAll
    public static void beforeAll() {
        loginCheck = mockStatic(LoginCheck.class);
    }

    @AfterAll
    public static void afterAll() {
        loginCheck.close();
    }

    @Test
    @DisplayName("SignUpController : 고객 회원가입")
    @WithMockUser
    void customerSignUp_URL_Test() throws Exception {
        Customer customer = Customer.from(customerSignUpForm);
        when(signUpApplication.customerSignUp(any(CustomerSignUpForm.class))).thenReturn(customer);

        String requestedURL = ACCOUNT_COMMON_URL + CUSTOMER_SIGNUP;
        ResultActions response = mockMvc.perform(post(requestedURL)
                .contentType(MediaType.APPLICATION_JSON)
                .with(csrf())
                .content(objectMapper.writeValueAsString(customerSignUpForm)));

        response.andExpect(status().isOk())
                .andExpect(content().string(customer.getEmail() + " 회원가입에 성공하였습니다."));


    }


    @Test
    @DisplayName("SignUpController : 판매자 회원가입")
    @WithMockUser
    void sellerSignUp() throws Exception {
        Seller seller = Seller.from(sellerSignUpForm);
        when(signUpApplication.sellerSignUp(any(SellerSignUpForm.class))).thenReturn(seller);

        String requestedURL = ACCOUNT_COMMON_URL + SELLER_SIGNUP;
        ResultActions response = mockMvc.perform(post(requestedURL)
                .contentType(MediaType.APPLICATION_JSON)
                .with(csrf())
                .content(objectMapper.writeValueAsString(sellerSignUpForm)));

        response.andExpect(status().isOk())
                .andExpect(content().string(seller.getEmail() + " 회원가입에 성공하였습니다."));
    }

    @Mock
    CustomerUpdateForm customerUpdateForm;

    @Test
    @WithMockUser
    void customerUpdate() throws Exception {
        //given
        when(customerUpdateForm.getEmail()).thenReturn("tester@test.com");
        when(customerUpdateForm.getName()).thenReturn("nameCanChange");
        when(customerUpdateForm.getPassword()).thenReturn("newPassword");
        when(customerUpdateForm.getPhone()).thenReturn("0000000");
        when(customerUpdateForm.getBirth()).thenReturn(LocalDate.of(1993,1,1));
        when(customerUpdateForm.getMembership()).thenReturn("VIP");

        Customer customer = Customer.builder()
                .email("tester@test.com")
                .name("nameCanChange")
                .password("newPassword")
                .birth(LocalDate.now())
                .phone("0000000")
                .membership("VIP")
                .build();
        //when
        when(LoginCheck.customerCheck(any(Authentication.class))).thenReturn(mock(CustomerPrincipalDetails.class));
        when(signUpApplication.customerUpdate(any(CustomerUpdateForm.class))).thenReturn(customer);
        String requestURL = ACCOUNT_COMMON_URL + CUSTOMER_UPDATE;
        ResultActions response = mockMvc.perform(put(requestURL)
                .contentType(MediaType.APPLICATION_JSON)
                .with(csrf())
                .content(objectMapper.writeValueAsString(customerUpdateForm)));
        //then
        response.andExpect(status().isOk());
    }
    @Mock
    SellerUpdateForm sellerUpdateForm;
            

    @Test
    @WithMockUser
    void sellerUpdate() throws Exception{
        when(sellerUpdateForm.getEmail()).thenReturn("tester@test.com");
        when(sellerUpdateForm.getName()).thenReturn("nameCanChange");
        when(sellerUpdateForm.getPassword()).thenReturn("newPassword");
        when(sellerUpdateForm.getPhone()).thenReturn("0000000");
        when(sellerUpdateForm.getBirth()).thenReturn(LocalDate.of(1993,1,1));

        Seller seller = Seller.builder()
                .email("tester@test.com")
                .name("nameCanChange")
                .password("newPassword")
                .birth(LocalDate.now())
                .phone("0000000")
                .build();
        //when
        when(LoginCheck.sellerCheck(any(Authentication.class))).thenReturn(mock(SellerPrincipalDetails.class));
        when(signUpApplication.sellerUpdate(any(SellerUpdateForm.class))).thenReturn(seller);
        String requestURL = ACCOUNT_COMMON_URL + SELLER_UPDATE;
        ResultActions response = mockMvc.perform(put(requestURL)
                .contentType(MediaType.APPLICATION_JSON)
                .with(csrf())
                .content(objectMapper.writeValueAsString(sellerUpdateForm)));
        //then
        response.andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void verifyCustomer() throws Exception {
        // given
        String email = "tester@test.com";
        String encryptedEmail = AESCryptoUtil.encrypt(AESCryptoUtil.specName, AESCryptoUtil.KEY, email);
        when(signUpApplication.customerVerify(email)).thenReturn(true);

        // when
        ResultActions response = mockMvc.perform(get(AccountAPIControllerProperties.ACCOUNT_COMMON_URL + AccountAPIControllerProperties.CUSTOMER_VERIFY + "/" + encryptedEmail)
                .contentType(MediaType.APPLICATION_JSON));
        //then
        response.andExpect(status().isOk())
                .andExpect(content().string("인증이 완료되었습니다."));
    }
    @Test
    @WithMockUser
    void verifySeller() throws Exception{
        // given
        String email = "tester@test.com";
        String encryptedEmail = AESCryptoUtil.encrypt(AESCryptoUtil.specName, AESCryptoUtil.KEY, email);
        when(signUpApplication.sellerVerify(email)).thenReturn(true);

        // when
        ResultActions response = mockMvc.perform(get(AccountAPIControllerProperties.ACCOUNT_COMMON_URL + AccountAPIControllerProperties.SELLER_VERIFY + "/" + encryptedEmail)
             //   .with(csrf())
                .contentType(MediaType.APPLICATION_JSON));
        // then
        response.andExpect(status().isOk())
                .andExpect(content().string("인증이 완료되었습니다."));

    }
}