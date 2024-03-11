package com.example.accountapi.application.tools;

import com.example.accountapi.web.validation.exception.AccountException;
import com.example.accountapi.web.validation.exception.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class LogAndException {
    public void emailVerificationErrorCheck(boolean isEmailVerificationSucceed, String toEmail){
        String emailVerificaitonIndicatior = isEmailVerificationSucceed ? "SUCCESS" : "FAIL";
        log.info("Send email result : "+ toEmail+"("+emailVerificaitonIndicatior+")");
        if(!isEmailVerificationSucceed){
            throw new AccountException(ErrorCode.VERIFICATION_EMAIL_ERROR);
        }
    }

    public void emailAlreadyExist(boolean isEmailExist){
        if(!isEmailExist){
            throw new AccountException(ErrorCode.ALREADY_REGISTER_USER);
        }
    }

    public void notFoundUserCheck(boolean isAccountExist) {
        if(!isAccountExist){
            throw new AccountException(ErrorCode.NOT_FOUND_USER);
        }
    }
}
