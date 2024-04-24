
package com.example.chatapi.config.common.validation;




import com.example.chatapi.config.common.SpringSecurity.id.UserPrincipalDetails;
import com.example.chatapi.config.common.validation.securityException.BasicException;
import com.example.chatapi.config.common.validation.securityException.BasicErrorCode;
import org.springframework.security.core.Authentication;

public class LoginCheckMSA {

    private static String customerType = "CUSTOMER";
    private static String sellerType = "SELLER";

    //TODO:로그아웃 기능 구현 필요.
    public void notLogin(Authentication authentication){
        if(authentication == null){
            return;
        }else{
            throw new BasicException(BasicErrorCode.NO_LOGIN_PLEASE);
        }
    }

    public static UserPrincipalDetails sellerCheck(Authentication authentication){
        if(authentication == null){
            throw new BasicException(BasicErrorCode.NOT_LOGIN_ERROR);
        }else{
            UserPrincipalDetails userPrincipalDetails = (UserPrincipalDetails) authentication.getPrincipal();

            if(!userPrincipalDetails.getType().equals(sellerType)){
                throw new BasicException(BasicErrorCode.LOGIN_TYPE_ERROR);
            }else{
                return userPrincipalDetails;
            }
        }
    }
    public static UserPrincipalDetails customerCheck(Authentication authentication) {
        if (authentication == null) {
            throw new BasicException(BasicErrorCode.NOT_LOGIN_ERROR);
        } else {
            UserPrincipalDetails userPrincipalDetails = (UserPrincipalDetails) authentication.getPrincipal();
            userPrincipalDetails.getType();
            if (!userPrincipalDetails.getType().equals(customerType)) {
                throw new BasicException(BasicErrorCode.LOGIN_TYPE_ERROR);
            }else{
                return userPrincipalDetails;
            }
        }
    }
    public static UserPrincipalDetails userCheck(Authentication authentication){
        if(authentication == null){
            throw new BasicException(BasicErrorCode.NOT_LOGIN_ERROR);
        }else{
            UserPrincipalDetails userPrincipalDetails = (UserPrincipalDetails) authentication.getPrincipal();
            return userPrincipalDetails;
        }
    }



    public static void customerCheck(String type){
         String CUSTOMER_Email  = "customer";

        if(!type.equals(CUSTOMER_Email)){
            throw new BasicException(BasicErrorCode.LOGIN_TYPE_ERROR);
        }
    }

    public static void sellerCheck(String type){
        String SELLER_Email = "seller";

        if(!type.equals(SELLER_Email)){
            throw new BasicException(BasicErrorCode.LOGIN_TYPE_ERROR);
        }
    }



}
