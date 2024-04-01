package com.example.accountapi.web.controller.account;

import com.example.accountapi.application.applications.signUp.SignUpApplication;
import com.example.accountapi.application.client.OrderServiceClient;
import com.example.accountapi.domain.dto.CustomerDto;
import com.example.accountapi.domain.model.Customer;
import com.example.accountapi.web.validation.exception.AccountException;
import com.example.accountapi.web.validation.form.customer.SignUpCustomerForm;
import com.example.accountapi.web.validation.form.customer.UpdateCustomerForm;
import com.example.accountapi.web.validation.form.seller.SignUpSellerForm;
import com.example.accountapi.web.validation.exception.ErrorCode;
import com.example.accountapi.config.SpringSecurity.customer.CustomerPrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(AccountAPIControllerProperties.ACCOUNT_COMMON_URL)
@RequiredArgsConstructor
public class SignUpController {

    private final SignUpApplication signUpApplication;

    @PostMapping(AccountAPIControllerProperties.CUSTOMER_SIGNUP)
    public ResponseEntity<String> customerSignUp(@Validated @RequestBody SignUpCustomerForm customerSignUpForm){
        return ResponseEntity.ok(signUpApplication.customerSignUp(customerSignUpForm));
    }
    @GetMapping(AccountAPIControllerProperties.CUSTOMER_VERIFY +"/{email}")
    public ResponseEntity<String> verifyCustomer(@PathVariable("email") String email){
        signUpApplication.customerVerify(email);
        return ResponseEntity.ok("인증이 완료되었습니다.");
    }

    @PostMapping("/hi")
    public String hicheck(){
        return "hi";
    }
    @PostMapping(AccountAPIControllerProperties.CUSTOMER_UPDATE)
    public ResponseEntity<CustomerDto> customerUpdate(@Validated @RequestBody UpdateCustomerForm updateCustomerForm, Authentication authentication){
        CustomerPrincipalDetails customerDetails = Optional.of((CustomerPrincipalDetails)authentication.getPrincipal()).orElseThrow(
                () -> new AccountException(ErrorCode.NOT_LOGIN_ERROR)
        );

        Customer customer = signUpApplication.customerUpdate(updateCustomerForm);

        return ResponseEntity.ok(CustomerDto.from(customer));
    }

    //Seller
    @PostMapping(AccountAPIControllerProperties.SELLER_SIGNUP)
    public ResponseEntity<String> sellerSignUp(@Validated @RequestBody SignUpSellerForm signUpSellerForm){
        return ResponseEntity.ok(signUpApplication.sellerSignUp(signUpSellerForm));
    }
    @PostMapping(AccountAPIControllerProperties.SELLER_UPDATE)
    public ResponseEntity sellerUpdate(){
        return null;
    }
    @GetMapping(AccountAPIControllerProperties.SELLER_VERIFY + "/{email}")
    public ResponseEntity<String> verifySeller(@PathVariable("email") String email){
        signUpApplication.sellerVerify(email);
        return ResponseEntity.ok("인증이 완료되었습니다.");
    }


    @Autowired
    OrderServiceClient orderServiceClient;
    @PostMapping("/feignTesting")
    public String feignTest(){
        String test = orderServiceClient.orderTesting().getBody();
        return test;
    }
}


