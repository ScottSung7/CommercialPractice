package com.example.accountapi.application.provider.emailVerification;

import com.example.accountapi.domain.model.Customer;
import com.example.accountapi.domain.model.Seller;

public interface EmailVerificationProvider {

     String sendVerificationEmail(Customer to);

     String sendVerificationEmail(Seller to);

}
