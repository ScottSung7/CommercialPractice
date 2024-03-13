package com.example.accountapi.web.controller.account;


import com.example.accountapi.application.applications.AccountInfo.AccountInfoApplication;
import com.example.accountapi.domain.dto.CustomerDto;
import com.example.accountapi.domain.dto.SellerDto;
import com.example.accountapi.domain.model.Customer;
import com.example.accountapi.domain.model.Seller;
import com.example.accountapi.web.validation.exception.AccountException;
import com.example.accountapi.web.validation.exception.ErrorCode;
import com.example.accountapi.config.SpringSecurity.customer.CustomerPrincipalDetails;
import com.example.accountapi.config.SpringSecurity.seller.SellerPrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

import static com.example.accountapi.web.controller.account.AccountAPIControllerProperties.ACCOUNT_COMMON_URL;

@RestController
@RequestMapping(ACCOUNT_COMMON_URL)
@RequiredArgsConstructor
public class AccountInfoController {

    private final AccountInfoApplication accountInfoApplication;

    @PostMapping("/customer")
    public ResponseEntity<CustomerDto> getCustomerInfo(Authentication authentication) {

        CustomerPrincipalDetails customerDetails = Optional.of((CustomerPrincipalDetails)authentication.getPrincipal()).orElseThrow(
                () -> new AccountException(ErrorCode.NOT_LOGIN_ERROR)
        );
        Customer customer = accountInfoApplication.findCustomer(customerDetails.getEmail());

        return ResponseEntity.ok(CustomerDto.from(customer));
    }

    @PostMapping("/seller")
    public ResponseEntity<SellerDto> getSellerInfo(Authentication authentication) {

        SellerPrincipalDetails sellerDetails = Optional.of((SellerPrincipalDetails)authentication.getPrincipal()).orElseThrow(
                () -> new AccountException(ErrorCode.NOT_LOGIN_ERROR)
        );
        Seller seller = accountInfoApplication.findSeller(sellerDetails.getEmail());

        return ResponseEntity.ok(SellerDto.from(seller));
    }
    
    
}
