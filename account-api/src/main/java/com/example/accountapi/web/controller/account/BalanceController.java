package com.example.accountapi.web.controller.account;

import com.example.accountapi.application.service.CustomerBalanceService;
import com.example.accountapi.domain.dto.CustomerBalanceDto;
import com.example.accountapi.domain.model.CustomerBalanceHistory;
import com.example.accountapi.web.validation.LoginCheck;
import com.example.accountapi.web.validation.form.ChangeBalanceForm;
import com.example.accountapi.config.SpringSecurity.id.customer.CustomerPrincipalDetails;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/accounts/customer")
@RequiredArgsConstructor
public class BalanceController {

    private final CustomerBalanceService customerBalanceService;

    @PostMapping("/balance")
    @Schema(name = "ChangeBalanceForm", implementation = ChangeBalanceForm.class)
    public ResponseEntity<CustomerBalanceDto> changeBalance(@Validated @RequestBody ChangeBalanceForm form, Authentication authentication){

        CustomerPrincipalDetails customerDetails = LoginCheck.customerCheck(authentication);
        CustomerBalanceHistory history = customerBalanceService.changeBalance(customerDetails.getEmail(), form);

        return ResponseEntity.ok(CustomerBalanceDto.from(history));
    }

    @PostMapping("/balance/check")
    public ResponseEntity<CustomerBalanceDto> checkBalance( Authentication authentication){
        CustomerPrincipalDetails customerDetails = LoginCheck.customerCheck(authentication);
        CustomerBalanceHistory history = customerBalanceService.checkBalance(customerDetails.getEmail());

        return ResponseEntity.ok(CustomerBalanceDto.currentMoneyFrom(history));
    }
}


