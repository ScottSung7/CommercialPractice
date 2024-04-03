package com.example.accountapi.web.validation;

import com.example.accountapi.application.tools.crpto.AESCryptoUtil;
import com.example.accountapi.config.SpringSecurity.customer.CustomerPrincipalDetails;
import com.example.accountapi.config.SpringSecurity.seller.SellerPrincipalDetails;
import com.example.accountapi.web.validation.exception.AccountException;
import com.example.accountapi.web.validation.exception.ErrorCode;
import org.springframework.security.core.Authentication;

import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.util.Optional;

import static com.example.accountapi.web.validation.exception.ErrorCode.NO_LOGIN_PLEASE;

public class LoginCheck {

    //TODO:로그아웃 기능 구현 필요.
    public void notLogin(Authentication authentication){
        if(authentication == null){
            return;
        }else{
            throw new AccountException(NO_LOGIN_PLEASE);
        }
    }

    public static SellerPrincipalDetails sellerCheck(Authentication authentication){
        if(authentication == null){
            throw new AccountException(ErrorCode.NOT_LOGIN_ERROR);
        }else{
            try {
               SellerPrincipalDetails sellerDetails = Optional.of((SellerPrincipalDetails) authentication.getPrincipal()).orElseThrow(
                        () -> new AccountException(ErrorCode.NOT_LOGIN_ERROR)
                );
               return sellerDetails;
            }catch (ClassCastException e){
                throw new AccountException(ErrorCode.LOGIN_TYPE_ERROR);
            }
        }
    }
    public static CustomerPrincipalDetails customerCheck(Authentication authentication){
        if(authentication == null){
            throw new AccountException(ErrorCode.NOT_LOGIN_ERROR);
        }else{
            try {
                CustomerPrincipalDetails customerDetails = Optional.of((CustomerPrincipalDetails) authentication.getPrincipal()).orElseThrow(
                        () -> new AccountException(ErrorCode.NOT_LOGIN_ERROR)
                );
                return customerDetails;
            }catch (ClassCastException e){
                throw new AccountException(ErrorCode.LOGIN_TYPE_ERROR);
            }
        }
    }


    public static void customerCheck(String type){
         String CUSTOMER_Email  = "customer";

        if(!type.equals(CUSTOMER_Email)){
            throw new AccountException(ErrorCode.LOGIN_TYPE_ERROR);
        }
    }

    public static void sellerCheck(String type){
        String SELLER_Email = "seller";

        if(!type.equals(SELLER_Email)){
            throw new AccountException(ErrorCode.LOGIN_TYPE_ERROR);
        }
    }



}
