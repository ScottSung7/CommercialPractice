package com.example.login.web.controller;

import com.example.login.domain.Account;
import com.example.login.domain.AccountType;
import com.example.login.repository.AccountRepository;
import com.example.login.service.AccountService;
import com.example.login.web.validation.form.AccountSaveForm;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequiredArgsConstructor
public class NewUserAccountController {

    private final AccountRepository accountRepository;

    private final AccountService accountService;
    private final String accountType = AccountType.USER.name();

    @PostMapping("/user/account")
    public Account createAccountPost(@Validated @ModelAttribute("account") AccountSaveForm form){

        Account account = Account.builder()
                .username(form.getName())
                .password(form.getPassword())
                .email(form.getEmail())
                .phoneNumber(form.getPhoneNumber())
                .role(accountType).build();

        return accountService.createNew(account);
    }


}
