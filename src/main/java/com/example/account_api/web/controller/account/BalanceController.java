package com.example.account_api.web.controller.account;

import com.example.account_api.application.service.CustomerBalanceService;
import com.example.account_api.web.validation.exception.AccountException;
import com.example.account_api.web.validation.exception.ErrorCode;
import com.example.account_api.web.validation.form.ChangeBalanceForm;
import com.example.config.SpringSecurity.CustomerPrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
public class BalanceController {

    private final CustomerBalanceService customerBalanceService;

    @PostMapping("/balance")
    public ResponseEntity<Integer> changeBalance(Authentication authentication, @RequestBody ChangeBalanceForm form) {
        CustomerPrincipalDetails customerDetails = Optional.of((CustomerPrincipalDetails) authentication.getPrincipal()).orElseThrow(
                () -> new AccountException(ErrorCode.NOT_LOGIN_ERROR)
        );
        return ResponseEntity.ok(customerBalanceService.changeBalance(customerDetails.getEmail(), form).getCurrentMoney());
    }
}


