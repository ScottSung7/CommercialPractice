package com.example.account_api.application.provider.emailVerification;

import com.example.account_api.application.provider.emailVerification.form.SendMailForm;
import com.example.account_api.domain.model.Customer;

import java.util.Optional;

public interface EmailVerificationProvider {

     String sendVerificationEmail(Customer to);
}
