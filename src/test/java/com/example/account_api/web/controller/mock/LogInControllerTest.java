package com.example.account_api.web.controller.mock;

import com.example.account_api.web.controller.LogInController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

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