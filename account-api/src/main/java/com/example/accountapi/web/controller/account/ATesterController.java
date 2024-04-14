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
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static com.example.accountapi.web.validation.exception.ErrorCode.*;

@RestController
@RequiredArgsConstructor
public class ATesterController {

    @Autowired
    private JWTUtil jwtUtil;
    private final SignUpCustomerService signUpCustomerService;
    private final SignUpSellerService signUpSellerService;
    private final AccountInfoApplication accountInfoApplication;
    private final ObjectMapper mapper;

    private Customer customer;
    private Seller seller;
    @PostMapping("test/create/customer")
    @Operation(summary = "구매자 \"test@test.com\" 유저를 등록하고 테스트에 필요한 JWT 토큰을 생성합니다.")
    public ResponseEntity<String> jtokenCustomer() throws JsonProcessingException {

        CustomerSignUpForm customerSignUpForm = CustomerSignUpForm.testSignUpForm();

        customer = signUpCustomerService.signUp(customerSignUpForm);

        System.out.println(LocalDateTime.now());



        return ResponseEntity.ok(makeKeyToJSON(customer.getEmail(), "CUSTOMER"));
    }

    @PostMapping("test/login/customer")
    @Operation(summary = "구매자 등록 후 토큰을 다시 받기 위해 요청합니다.")
    public ResponseEntity<String> jtokenLoginCustomer() throws JsonProcessingException {
        String email = "tester@test.com";
        if(accountInfoApplication.findCustomer(email) == null) {  // customer가 null이면
            throw new AccountException(NO_REGISTERED_TEST_CUSTOMER);
        }

        return ResponseEntity.ok(makeKeyToJSON(customer.getEmail(), "CUSTOMER"));
    }

    @PostMapping("test/create/seller")
    @Operation(summary = "판매자 \"test@test.com\" 유저를 등록하고 테스트에 필요한 JWT 토큰을 생성합니다.")
    public ResponseEntity<String> jtokenSeller() throws JsonProcessingException {
        SellerSignUpForm sellerSignUpForm = SellerSignUpForm.testSignUpForm();
        seller = signUpSellerService.signUp(sellerSignUpForm);

        return ResponseEntity.ok(makeKeyToJSON(seller.getEmail(), "SELLER"));
    }

    @PostMapping("test/login/seller")
    @Operation(summary = "판매자 등록 후 토큰을 다시 받기 위해 요청합니다.")
    public ResponseEntity<String> jtokenLoginSeller() throws JsonProcessingException {
        String email = "tester@test.com";
        if(accountInfoApplication.findSeller(email) == null) {
            throw new AccountException(NO_REGISTERED_TEST_SELLER);
        }
        return ResponseEntity.ok(makeKeyToJSON(seller.getEmail(), "SELLER"));
    }

    private String makeKeyToJSON(String email, String type) throws JsonProcessingException {
        Map<String, String> map = new HashMap();
        Long id;
        if(type.equals("CUSTOMER")){
            Customer customer = accountInfoApplication.findCustomer(email);
            id = customer.getId();
        }else if(type.equals("SELLER")){
            Seller seller = accountInfoApplication.findSeller(email);
            id = seller.getId();
        }else{
            throw new AccountException(NOT_FOUND_USER);
        }

        String key = jwtUtil.createJwt(email,type, id, 60 * 60 * 10 * 1000L);
        map.put("key", key);

        return mapper.writeValueAsString(map);
    }

}
