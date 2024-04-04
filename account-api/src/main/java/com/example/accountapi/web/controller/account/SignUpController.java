package com.example.accountapi.web.controller.account;

import com.example.accountapi.application.applications.signUp.SignUpApplication;
import com.example.accountapi.application.tools.crpto.AESCryptoUtil;
import com.example.accountapi.domain.dto.CustomerDto;
import com.example.accountapi.domain.dto.SellerDto;
import com.example.accountapi.domain.model.Customer;
import com.example.accountapi.domain.model.Seller;
import com.example.accountapi.web.validation.LoginCheck;
import com.example.accountapi.web.validation.exception.AccountException;
import com.example.accountapi.web.validation.form.customer.CustomerSignUpForm;
import com.example.accountapi.web.validation.form.customer.CustomerUpdateForm;
import com.example.accountapi.web.validation.form.seller.SellerSignUpForm;
import com.example.accountapi.web.validation.exception.ErrorCode;
import com.example.accountapi.web.validation.form.seller.SellerUpdateForm;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(AccountAPIControllerProperties.ACCOUNT_COMMON_URL)
@RequiredArgsConstructor
public class SignUpController {

    private final SignUpApplication signUpApplication;

    @PostMapping(AccountAPIControllerProperties.CUSTOMER_SIGNUP)
    @Operation(summary = "이메일 인증 테스트 (기능의 경우 a-tester-controller로 진행 가능)",
            description = "토큰 발급은 되지 않습니다. 기능 테스트는 a-tester-controller를 이용 부탁드립니다.")
    @Schema(name = "CustomerSignUpForm", implementation = CustomerSignUpForm.class)
    public ResponseEntity<String> customerSignUp(@Validated @RequestBody CustomerSignUpForm customerSignUpForm) {

        Customer custoemr = signUpApplication.customerSignUp(customerSignUpForm);
        return ResponseEntity.ok(custoemr.getEmail() + " 회원가입에 성공하였습니다.");
    }

    @PostMapping(AccountAPIControllerProperties.SELLER_SIGNUP)
    @Operation(summary = "이메일 인증 테스트 (기능의 경우 a-tester-controller로 진행 가능)",
            description = "토큰 발급은 되지 않습니다. 기능 테스트는 a-tester-controller를 이용 부탁드립니다.")
    @Schema(name = "SellerSignUpForm", implementation = SellerSignUpForm.class)
    public ResponseEntity<String> sellerSignUp(@Validated @RequestBody SellerSignUpForm sellerSignUpForm) {

        Seller seller = signUpApplication.sellerSignUp(sellerSignUpForm);
        return ResponseEntity.ok(seller.getEmail() + " 회원가입에 성공하였습니다.");
    }


    @PutMapping(AccountAPIControllerProperties.CUSTOMER_UPDATE)
    @Schema(name = "CustomerUpdateForm", implementation = CustomerUpdateForm.class)
    public ResponseEntity<CustomerDto> customerUpdate(@Validated @RequestBody CustomerUpdateForm customerUpdateForm, Authentication authentication) {

        LoginCheck.customerCheck(authentication);
        Customer customer = signUpApplication.customerUpdate(customerUpdateForm);
        return ResponseEntity.ok(CustomerDto.from(customer));
    }

    @PutMapping(AccountAPIControllerProperties.SELLER_UPDATE)
    @Schema(name = "SellerUpdateForm", implementation = SellerUpdateForm.class)
    public ResponseEntity<SellerDto> sellerUpdate(@Validated @RequestBody SellerUpdateForm form, Authentication authentication) {
        LoginCheck.sellerCheck(authentication);
        Seller seller = signUpApplication.sellerUpdate(form);
        return ResponseEntity.ok(SellerDto.from(seller));
    }

    @GetMapping(AccountAPIControllerProperties.CUSTOMER_VERIFY + "/{email}")
    @Operation(summary="이 요청으로 테스트를 하려면 개인 이메일로 받은 암호화된 문자열이 필요합니다."
            , description = "받으신 이메일의 링크 클릭으로도 인증이 가능합니다.")
    public ResponseEntity<String> verifyCustomer(@PathVariable("email") String email){

      //  String decrptedType = decript(type);
      //  LoginCheck.customerCheck(decrptedType);

        String decrptedEmail = decript(email);
        signUpApplication.customerVerify(decrptedEmail);
        return ResponseEntity.ok("인증이 완료되었습니다.");
    }

    @GetMapping(AccountAPIControllerProperties.SELLER_VERIFY + "/{email}")
    @Operation(summary="이 요청으로 테스트를 하려면 개인 이메일로 받은 암호화된 문자열이 필요합니다."
            , description = "받으신 이메일의 링크 클릭으로도 인증이 가능합니다.")
    public ResponseEntity<String> verifySeller(@PathVariable("email") String email) {

      //  String decrptedType = decript(type);
     //   LoginCheck.sellerCheck(decrptedType);

        String decrptedEmail = decript(email);
        signUpApplication.sellerVerify(decrptedEmail);
        return ResponseEntity.ok("인증이 완료되었습니다.");
    }

    private String decript(String value) {
        try {
            return AESCryptoUtil.decrypt(AESCryptoUtil.specName, AESCryptoUtil.KEY, value);
        } catch (Exception e) {
            throw new AccountException(ErrorCode.INVALID_DECRYPT_ERROR);
        }
    }

//    @Autowired
//    OrderServiceClient orderServiceClient;
//
//    @PostMapping("/feignTesting")
//    public String feignTest() {
//        String test = orderServiceClient.orderTesting().getBody();
//        return test;
//    }


}


