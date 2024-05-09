package com.example.accountapi.application.tools.provider.emailVerification;

import com.example.accountapi.domain.model.Customer;
import com.example.accountapi.domain.model.Seller;

public interface EmailVerificationProvider {


     public boolean sendVerificationEmail(String email, String name, String type);

}
