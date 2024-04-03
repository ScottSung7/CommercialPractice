package com.example.accountapi.application.applications.signUp;

import com.example.accountapi.application.provider.emailVerification.EmailVerificationProvider;
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

        if(signUpCustomerService.isEmailExist(customerSignUpForm.getEmail())){
          throw new AccountException(ErrorCode.ALREADY_REGISTER_USER);
        };
        Customer signUpCustomer = signUpCustomerService.signUp(customerSignUpForm);

        emailVerificationProvider.sendVerificationEmail(signUpCustomer);
        signUpCustomerService.changeCustomerValidateEmail(signUpCustomer);

        return signUpCustomer;
    }
    @Override
    public void customerVerify(String email){
        signUpCustomerService.customerVerify(email);
    }
    @Override
    public Customer customerUpdate(CustomerUpdateForm customerUpdateForm) {

        return signUpCustomerService.update(customerUpdateForm);
    }

    @Override
    public Seller sellerSignUp(SellerSignUpForm sellerSignUpForm) {
        if(signUpSellerService.isEmailExist(sellerSignUpForm.getEmail())){
            throw new AccountException(ErrorCode.ALREADY_REGISTER_USER);
        }
        Seller signUpSeller = signUpSellerService.signUp(sellerSignUpForm);

        emailVerificationProvider.sendVerificationEmail(signUpSeller);
        signUpSellerService.changeSellerValidateEmail(signUpSeller);

        return signUpSeller;
    }

    @Override
    public Seller sellerUpdate(SellerUpdateForm sellerUpdateForm) {
        return signUpSellerService.update(sellerUpdateForm);
    }

    @Override
    public void sellerVerify(String email){
        signUpSellerService.sellerVerify(email);
    }



}
