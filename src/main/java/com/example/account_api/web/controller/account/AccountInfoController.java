package com.example.account_api.web.controller.account;


import com.example.account_api.application.service.accountInfo.customer.AccountInfoCustomerService;
import com.example.account_api.application.service.accountInfo.seller.AccountInfoSellerService;
import com.example.account_api.domain.dto.CustomerDto;
import com.example.account_api.domain.dto.SellerDto;
import com.example.account_api.domain.model.Customer;
import com.example.account_api.domain.model.Seller;
import com.example.account_api.web.validation.exception.AccountException;
import com.example.account_api.web.validation.exception.ErrorCode;
import com.example.config.SpringSecurity.CustomerPrincipalDetails;
import com.example.config.SpringSecurity.SellerPrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

import static com.example.account_api.web.controller.account.AccountAPIControllerProperties.ACCOUNT_COMMON_URL;

@RestController
@RequestMapping(ACCOUNT_COMMON_URL)
@RequiredArgsConstructor
public class AccountInfoController {

    private final AccountInfoCustomerService accountInfoCustomerService;
    private final AccountInfoSellerService accountInfoSellerService;
    @PostMapping("/customer")
    public ResponseEntity<CustomerDto> getCustomerInfo(Authentication authentication) {

        CustomerPrincipalDetails customerDetails = Optional.of((CustomerPrincipalDetails)authentication.getPrincipal()).orElseThrow(
                () -> new AccountException(ErrorCode.NOT_LOGIN_ERROR)
        );
        Customer customer = accountInfoCustomerService.findCustomer(customerDetails.getEmail());

        return ResponseEntity.ok(CustomerDto.from(customer));
    }

    @PostMapping("/seller")
    public ResponseEntity<SellerDto> getSellerInfo(Authentication authentication) {

        SellerPrincipalDetails sellerDetails = Optional.of((SellerPrincipalDetails)authentication.getPrincipal()).orElseThrow(
                () -> new AccountException(ErrorCode.NOT_LOGIN_ERROR)
        );
        Seller seller = accountInfoSellerService.findSeller(sellerDetails.getEmail());

        return ResponseEntity.ok(SellerDto.from(seller));
    }
    
    
}
