package com.example.account_api.web.controller.account;


import com.example.account_api.application.service.accountInfo.AccountInfoCustomerService;
import com.example.account_api.domain.model.Customer;
import com.example.account_api.web.validation.exception.AccountException;
import com.example.account_api.web.validation.exception.ErrorCode;
import com.example.config.SpringSecurity.CustomerPrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

import static com.example.account_api.web.controller.account.AccountAPIControllerProperties.ACCOUNT_COMMON_URL;

@RestController
@RequestMapping(ACCOUNT_COMMON_URL)
@RequiredArgsConstructor
public class AccountInfoController {

    private final AccountInfoCustomerService accountInfoCustomerService;
    @GetMapping("/customer")
    public Customer getUserInfo(Authentication authentication) {
        System.out.println("checking");
        System.out.println(authentication.getName());

        CustomerPrincipalDetails customerDetails = Optional.of((CustomerPrincipalDetails)authentication.getPrincipal()).orElseThrow(
                () -> new AccountException(ErrorCode.NOT_LOGIN_ERROR)
        );

        return accountInfoCustomerService.findCustomer(customerDetails.getEmail());
    }


}
