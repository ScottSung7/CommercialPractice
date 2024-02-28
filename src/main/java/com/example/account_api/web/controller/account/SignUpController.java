package com.example.account_api.web.controller.account;

import com.example.account_api.application.applications.signUp.SignUpApplication;
import com.example.account_api.application.service.signIn.customer.SignUpCustomerService;
import com.example.account_api.domain.dto.CustomerDto;
import com.example.account_api.domain.model.Customer;
import com.example.account_api.web.validation.exception.AccountException;
import com.example.account_api.web.validation.exception.ErrorCode;
import com.example.account_api.web.validation.form.customer.SignUpCustomerForm;
import com.example.account_api.web.validation.form.customer.UpdateCustomerForm;
import com.example.account_api.web.validation.form.seller.SignUpSellerForm;
import com.example.config.SpringSecurity.CustomerPrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

import static com.example.account_api.web.controller.account.AccountAPIControllerProperties.*;

@RestController
@RequestMapping(ACCOUNT_COMMON_URL)
@RequiredArgsConstructor
public class SignUpController {

    private final SignUpApplication signUpApplication;

    @PostMapping(CUSTOMER_SIGNUP)
    public ResponseEntity<String> customerSignUp(@Validated @RequestBody SignUpCustomerForm customerSignUpForm){
        return ResponseEntity.ok(signUpApplication.customerSignUp(customerSignUpForm));
    }
    @GetMapping(CUSTOMER_VERIFY +"/{email}")
    public ResponseEntity<String> verifyCustomer(@PathVariable("email") String email){
        signUpApplication.customerVerify(email);
        return ResponseEntity.ok("인증이 완료되었습니다.");
    }

    @PostMapping("/hi")
    public String hicheck(){
        return "hi";
    }
    @PostMapping(CUSTOMER_UPDATE)
    public ResponseEntity<CustomerDto> customerUpdate(@Validated @RequestBody UpdateCustomerForm updateCustomerForm, Authentication authentication){
        CustomerPrincipalDetails customerDetails = Optional.of((CustomerPrincipalDetails)authentication.getPrincipal()).orElseThrow(
                () -> new AccountException(ErrorCode.NOT_LOGIN_ERROR)
        );

        Customer customer = signUpApplication.customerUpdate(updateCustomerForm);

        return ResponseEntity.ok(CustomerDto.from(customer));
    }

    //Seller
    @PostMapping(SELLER_SIGNUP)
    public ResponseEntity<String> sellerSignUp(@Validated @RequestBody SignUpSellerForm signUpSellerForm){
        return ResponseEntity.ok(signUpApplication.sellerSignUp(signUpSellerForm));
    }
    @PostMapping(SELLER_UPDATE)
    public ResponseEntity sellerUpdate(){
        return null;
    }
    @GetMapping(SELLER_VERIFY + "/{email}")
    public ResponseEntity<String> verifySeller(@PathVariable("email") String email){
        signUpApplication.sellerVerify(email);
        return ResponseEntity.ok("인증이 완료되었습니다.");
    }

    @PostMapping("/hi3")
    public String hi(){
        throw new AccountException(ErrorCode.UNKNOWN_ERROR);
    }


}


