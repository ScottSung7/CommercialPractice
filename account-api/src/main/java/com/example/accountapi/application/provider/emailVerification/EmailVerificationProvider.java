package com.example.accountapi.application.provider.emailVerification;

import com.example.accountapi.domain.model.Customer;
import com.example.accountapi.domain.model.Seller;

public interface EmailVerificationProvider {

     public void sendVerificationEmail(Customer to);

     public void sendVerificationEmail(Seller to);

}
