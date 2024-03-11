package com.example.accountapi.web.controller.mock;

import com.example.accountapi.web.controller.account.LogInController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class LogInControllerTest {

    @InjectMocks
    private LogInController logInController;


    @Test
    void customerLogin() {
    }

    @Test
    void customerVerify() {
    }

    @Test
    void sellerLogin() {
    }

    @Test
    void sellerVerify() {
    }
}