package com.example.account_api.web.validation.form;

import com.example.account_api.application.SignUpApplication;
import com.example.account_api.web.controller.SignUpController;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import lombok.Builder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;
import java.util.Set;

import static com.example.account_api.web.controller.AccountAPIControllerProperties.ACCOUNT_COMMON_URL;
import static com.example.account_api.web.controller.AccountAPIControllerProperties.CUSTOMER_SIGNUP;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class SignUpCustomerForm_ValidationTest {

    private Validator validator;

    private SignUpCustomerForm tester =
                    SignUpCustomerForm.builder()
                            .name("tester")
                            .email("test@test.com")
                            .password("1234")
                            .phone("112233")
                            .birth(LocalDate.of(1991,2,3))
                            .build();

    @BeforeEach
    public void initEach(){
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    @DisplayName("이메일 필수 확인")
    public void email_not_blank_test() throws Exception {
        String testField = "email";
        String testViolation = "must not be blank";

        SignUpCustomerForm signUpCustomerForm =
                SignUpCustomerForm.builder()
                        .name(tester.getName())
                        .password(tester.getPassword())
                        .birth(tester.getBirth())
                        .phone(tester.getPhone())
                        .build();

        Set<ConstraintViolation<SignUpCustomerForm>> violations =
                validator.validate(signUpCustomerForm);
        ConstraintViolation<?> constraintViolation = violations.stream().findFirst().get();

        assertEquals(violations.size(), 1);
        assertEquals(constraintViolation.getPropertyPath().toString(), testField);
        assertEquals(constraintViolation.getMessage().toString(), testViolation);
    }

    @Test
    @DisplayName("이름 필수 확인")
    public void name_not_blank_test() throws Exception {
        String testField = "name";
        String testViolation = "must not be blank";

        SignUpCustomerForm signUpCustomerForm =
                SignUpCustomerForm.builder()
                        .email(tester.getEmail())
                        .password(tester.getPassword())
                        .birth(tester.getBirth())
                        .phone(tester.getPhone())
                        .build();

        Set<ConstraintViolation<SignUpCustomerForm>> violations =
                validator.validate(signUpCustomerForm);
        ConstraintViolation<?> constraintViolation = violations.stream().findFirst().get();

        assertEquals(violations.size(), 1);
        assertEquals(constraintViolation.getPropertyPath().toString(), testField);
        assertEquals(constraintViolation.getMessage().toString(), testViolation);
    }
    @Test
    @DisplayName("비밀번호 필수 확인")
    public void password_not_blank_test() throws Exception {
        String testField = "password";
        String testViolation = "must not be blank";

        SignUpCustomerForm signUpCustomerForm =
                SignUpCustomerForm.builder()
                        .email(tester.getEmail())
                        .name(tester.getName())
                        .birth(tester.getBirth())
                        .phone(tester.getPhone())
                        .build();

        Set<ConstraintViolation<SignUpCustomerForm>> violations =
                validator.validate(signUpCustomerForm);
        ConstraintViolation<?> constraintViolation = violations.stream().findFirst().get();

        assertEquals(violations.size(), 1);
        assertEquals(constraintViolation.getPropertyPath().toString(), testField);
        assertEquals(constraintViolation.getMessage().toString(), testViolation);
    }

    @Test
    @DisplayName("생일 필수 확인")
    public void birth_not_null_test() throws Exception {
        String testField = "birth";
        String testViolation = "must not be null";

        SignUpCustomerForm signUpCustomerForm =
                SignUpCustomerForm.builder()
                        .name(tester.getName())
                        .email(tester.getEmail())
                        .password(tester.getPassword())
                        .phone(tester.getPhone())
                        .build();

        Set<ConstraintViolation<SignUpCustomerForm>> violations =
                validator.validate(signUpCustomerForm);
        ConstraintViolation<?> constraintViolation = violations.stream().findFirst().get();

        assertEquals(violations.size(), 1);
        assertEquals(constraintViolation.getPropertyPath().toString(), testField);
        assertEquals(constraintViolation.getMessage().toString(), testViolation);
    }

    @Test
    @DisplayName("휴대폰 번호 필수 확인")
    public void phone_not_blank_test() throws Exception {
        String testField = "phone";
        String testViolation = "must not be blank";

        SignUpCustomerForm signUpCustomerForm =
                SignUpCustomerForm.builder()
                        .name(tester.getName())
                        .email(tester.getEmail())
                        .password(tester.getPassword())
                        .birth(tester.getBirth())
                        .build();

        Set<ConstraintViolation<SignUpCustomerForm>> violations =
                validator.validate(signUpCustomerForm);
        ConstraintViolation<?> constraintViolation = violations.stream().findFirst().get();

        assertEquals(violations.size(), 1);
        assertEquals(constraintViolation.getPropertyPath().toString(), testField);
        assertEquals(constraintViolation.getMessage().toString(), testViolation);
    }
}