package com.example.invite.controller;

import com.example.invite.domain.Account;
import com.example.invite.repository.AccountRepository;
import com.example.invite.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AccountController {

    private final AccountRepository accountRepository;

    private final AccountService service;

    @GetMapping("/account/{role}/{username}/{password}")
    public Account createAccount(@ModelAttribute Account account){
        return service.createNew(account);

    }
    @GetMapping("/test")
    public void testing(){
        service.dashboard();
    }
}
