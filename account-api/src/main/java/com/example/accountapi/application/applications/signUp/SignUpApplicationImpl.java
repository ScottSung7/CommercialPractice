package com.example.accountapi.application.applications.signUp;

import com.example.accountapi.application.tools.provider.emailVerification.EmailVerificationProvider;
import com.example.accountapi.application.service.signUp.customer.SignUpCustomerService;
import com.example.accountapi.application.service.signUp.seller.SignUpSellerService;
import com.example.accountapi.domain.model.Customer;
import com.example.accountapi.domain.model.Seller;
import com.example.accountapi.web.validation.exception.AccountException;
import com.example.accountapi.web.validation.form.customer.CustomerSignUpForm;
import com.example.accountapi.web.validation.form.customer.CustomerUpdateForm;
import com.example.accountapi.web.validation.form.seller.SellerSignUpForm;
import com.example.accountapi.web.validation.exception.ErrorCode;
import com.example.accountapi.web.validation.form.seller.SellerUpdateForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Service
@Slf4j
@RequiredArgsConstructor
public class SignUpApplicationImpl implements SignUpApplication {

    private final SignUpCustomerService signUpCustomerService;
    private final SignUpSellerService signUpSellerService;
    private final EmailVerificationProvider emailVerificationProvider;


    @Override
    public Customer customerSignUp(CustomerSignUpForm customerSignUpForm){
        String urlType = "customer";

        if(signUpCustomerService.isEmailExist(customerSignUpForm.getEmail())){
          throw new AccountException(ErrorCode.ALREADY_REGISTER_USER);
        };
        Customer signUpCustomer = signUpCustomerService.signUp(customerSignUpForm);

        boolean mailSentCheck =
                emailVerificationProvider.sendVerificationEmail(signUpCustomer.getEmail(), signUpCustomer.getName(), urlType);
        if(mailSentCheck) {
            signUpCustomerService.addExpirationDate(signUpCustomer, 1);
        }else{
            throw new AccountException(ErrorCode.VERIFICATION_EMAIL_ERROR);
        }
        return signUpCustomer;
    }


    @Override
    public boolean customerVerify(String email){
        return signUpCustomerService.customerVerify(email);
    }
    @Override
    public Customer customerUpdate(CustomerUpdateForm customerUpdateForm) {

        return signUpCustomerService.update(customerUpdateForm);
    }

    @Override
    public Seller sellerSignUp(SellerSignUpForm sellerSignUpForm) {
        String urlType = "seller";
        if(signUpSellerService.isEmailExist(sellerSignUpForm.getEmail())){
            throw new AccountException(ErrorCode.ALREADY_REGISTER_USER);
        }
        Seller signUpSeller = signUpSellerService.signUp(sellerSignUpForm);

        boolean mailSentCheck =
                emailVerificationProvider.sendVerificationEmail(signUpSeller.getEmail(), signUpSeller.getName(), urlType);
        if(mailSentCheck) {
            signUpSellerService.addExpirationDate(signUpSeller, 1);
        }else{
            throw new AccountException(ErrorCode.VERIFICATION_EMAIL_ERROR);
        }

        return signUpSeller;
    }

    @Override
    public Seller sellerUpdate(SellerUpdateForm sellerUpdateForm) {
        return signUpSellerService.update(sellerUpdateForm);
    }

    @Override
    public boolean sellerVerify(String email){
        return signUpSellerService.sellerVerify(email);
    }


}
