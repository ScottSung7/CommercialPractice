package com.example.account_api.application.tools;

import com.example.account_api.web.validation.exception.AccountException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.example.account_api.web.validation.exception.ErrorCode.*;

@Slf4j
@Service
public class LogAndException {
    public void emailVerificationErrorCheck(boolean isEmailVerificationSucceed, String toEmail){
        String emailVerificaitonIndicatior = isEmailVerificationSucceed ? "SUCCESS" : "FAIL";
        log.info("Send email result : "+ toEmail+"("+emailVerificaitonIndicatior+")");
        if(!isEmailVerificationSucceed){
            throw new AccountException(VERIFICATION_EMAIL_ERROR);
        }
    }

    public void emailAlreadyExist(boolean isEmailExist){
        if(!isEmailExist){
            throw new AccountException(ALREADY_REGISTER_USER);
        }
    }

    public void notFoundUserCheck(boolean isAccountExist) {
        if(!isAccountExist){
            throw new AccountException(NOT_FOUND_USER);
        }
    }
}
