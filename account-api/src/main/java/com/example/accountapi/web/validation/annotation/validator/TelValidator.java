package com.example.accountapi.web.validation.annotation.validator;

import com.example.accountapi.web.validation.annotation.Tel;
import com.example.accountapi.web.validation.exception.AccountException;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import static com.example.accountapi.web.validation.exception.ErrorCode.NO_DASH;

public class TelValidator implements ConstraintValidator<Tel, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(value == null) return false;
        if(value.contains("-")){
            return false;
        }else{
            return true;
        }
    }
}
