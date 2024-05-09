
package com.example.orderapi.config.common.validation;



import com.example.orderapi.config.common.SpringSecurity.id.UserPrincipalDetails;
import com.example.orderapi.config.common.validation.accountException.AccountException;
import com.example.orderapi.config.common.validation.accountException.ErrorCode;
import org.springframework.security.core.Authentication;

public class LoginCheckMSA {

    private static String customerType = "CUSTOMER";
    private static String sellerType = "SELLER";

    //TODO:로그아웃 기능 구현 필요.
    public static void notLogin(Authentication authentication){
        if(authentication == null){
            return;
        }else{
            throw new AccountException(ErrorCode.NO_LOGIN_PLEASE);
        }
    }

    public static UserPrincipalDetails sellerCheck(Authentication authentication){
        if(authentication == null){
            throw new AccountException(ErrorCode.NOT_LOGIN_ERROR);
        }else{
            UserPrincipalDetails userPrincipalDetails = (UserPrincipalDetails) authentication.getPrincipal();

            if(!userPrincipalDetails.getType().equals(sellerType)){
                throw new AccountException(ErrorCode.LOGIN_TYPE_ERROR);
            }else{
                return userPrincipalDetails;
            }
        }
    }
    public static UserPrincipalDetails customerCheck(Authentication authentication) {
        if (authentication == null) {
            throw new AccountException(ErrorCode.NOT_LOGIN_ERROR);
        } else {
            UserPrincipalDetails userPrincipalDetails = (UserPrincipalDetails) authentication.getPrincipal();
            userPrincipalDetails.getType();
            if (!userPrincipalDetails.getType().equals(customerType)) {
                throw new AccountException(ErrorCode.LOGIN_TYPE_ERROR);
            }else{
                return userPrincipalDetails;
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
