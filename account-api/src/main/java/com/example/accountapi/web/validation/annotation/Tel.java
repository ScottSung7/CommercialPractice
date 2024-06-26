package com.example.accountapi.web.validation.annotation;

import com.example.accountapi.web.validation.annotation.validator.TelValidator;
import jakarta.validation.Constraint;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = TelValidator.class)
public @interface Tel {
    String message() default "휴대폰 번호";

    Class[] groups() default {};
    Class[] payload() default {};
}
