package com.example.account_api.application.applications.signUp;

import com.example.account_api.application.provider.emailVerification.EmailVerificationProvider;
import com.example.account_api.application.service.signIn.customer.SignUpCustomerService;
import com.example.account_api.application.service.signIn.seller.SignUpSellerService;
import com.example.account_api.domain.model.Customer;
import com.example.account_api.domain.model.Seller;
import com.example.account_api.web.validation.exception.AccountException;
import com.example.account_api.web.validation.form.customer.SignUpCustomerForm;
import com.example.account_api.web.validation.form.customer.UpdateCustomerForm;
import com.example.account_api.web.validation.form.seller.SignUpSellerForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.example.account_api.web.validation.exception.ErrorCode.ALREADY_REGISTER_USER;

@Service
@Slf4j
@RequiredArgsConstructor
public class SignUpApplicationImpl implements SignUpApplication {

    private final SignUpCustomerService signUpCustomerService;
    private final SignUpSellerService signUpSellerService;

    private final EmailVerificationProvider emailVerificationProvider;

    @Override
    public String customerSignUp(SignUpCustomerForm signUpCustomerForm){

        if(signUpCustomerService.isEmailExist(signUpCustomerForm.getEmail())){
          throw new AccountException(ALREADY_REGISTER_USER);
        };
        Customer signUpCustomer = signUpCustomerService.signUp(signUpCustomerForm);

        //emailVerificationProvider.sendVerificationEmail(signUpCustomer);
        signUpCustomerService.changeCustomerValidateEmail(signUpCustomer);

        return signUpCustomer.getEmail()+ " 회원가입에 성공하였습니다.";
    }
    @Override
    public void customerVerify(String email){
        signUpCustomerService.customerVerify(email);
    }
    @Override
    public Customer customerUpdate(UpdateCustomerForm updateCustomerForm) {

        return signUpCustomerService.update(updateCustomerForm);
    }

    @Override
    public String sellerSignUp(SignUpSellerForm signUpSellerForm) {
        if(signUpSellerService.isEmailExist(signUpSellerForm.getEmail())){
            throw new AccountException(ALREADY_REGISTER_USER);
        }
        Seller signUpSeller = signUpSellerService.signUp(signUpSellerForm);

    //    emailVerificationProvider.sendVerificationEmail(signUpSeller);
        signUpSellerService.changeSellerValidateEmail(signUpSeller);

        return signUpSeller.getEmail()+ " 회원가입에 성공하였습니다.";
    }
    @Override
    public void sellerVerify(String email){
        signUpSellerService.sellerVerify(email);
    }



}
