//package com.example.accountapi.web.validation.form;
//
//import com.example.accountapi.web.validation.form.seller.SellerSignUpForm;
//import jakarta.validation.ConstraintViolation;
//import jakarta.validation.Validation;
//import jakarta.validation.Validator;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Disabled;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//
//import java.time.LocalDate;
//import java.util.Set;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//@Disabled
//class SellerSignUpForm_ValidationTest {
//
//    private Validator validator;
//
//    private SellerSignUpForm tester =
//                    SellerSignUpForm.builder()
//                            .name("tester")
//                            .email("test@test.com")
//                            .password("1234")
//                            .phone("112233")
//                            .birth(LocalDate.of(1991,2,3))
//                            .companyRegistrationNumber("333")
//                            .build();
//
//    @BeforeEach
//    public void initEach(){
//        validator = Validation.buildDefaultValidatorFactory().getValidator();
//    }
//
//    @Test
//    @DisplayName("이메일 필수 확인")
//    public void email_not_blank_test() throws Exception {
//        String testField = "email";
//        String testViolation = "must not be blank";
//
//        SellerSignUpForm sellerSignUpForm =
//                SellerSignUpForm.builder()
//                        .name(tester.getName())
//                        .password(tester.getPassword())
//                        .birth(tester.getBirth())
//                        .phone(tester.getPhone())
//                        .build();
//
//        Set<ConstraintViolation<SellerSignUpForm>> violations =
//                validator.validate(sellerSignUpForm);
//        ConstraintViolation<?> constraintViolation = violations.stream().findFirst().get();
//
//        assertEquals(violations.size(), 1);
//        assertEquals(constraintViolation.getPropertyPath().toString(), testField);
//        assertEquals(constraintViolation.getMessage().toString(), testViolation);
//    }
//
//    @Test
//    @DisplayName("이름 필수 확인")
//    public void name_not_blank_test() throws Exception {
//        String testField = "name";
//        String testViolation = "must not be blank";
//
//        SellerSignUpForm sellerSignUpForm =
//                SellerSignUpForm.builder()
//                        .email(tester.getEmail())
//                        .password(tester.getPassword())
//                        .birth(tester.getBirth())
//                        .phone(tester.getPhone())
//                        .build();
//
//        Set<ConstraintViolation<SellerSignUpForm>> violations =
//                validator.validate(sellerSignUpForm);
//        ConstraintViolation<?> constraintViolation = violations.stream().findFirst().get();
//
//        assertEquals(violations.size(), 1);
//        assertEquals(constraintViolation.getPropertyPath().toString(), testField);
//        assertEquals(constraintViolation.getMessage().toString(), testViolation);
//    }
//    @Test
//    @DisplayName("비밀번호 필수 확인")
//    public void password_not_blank_test() throws Exception {
//        String testField = "password";
//        String testViolation = "must not be blank";
//
//        SellerSignUpForm sellerSignUpForm =
//                SellerSignUpForm.builder()
//                        .email(tester.getEmail())
//                        .name(tester.getName())
//                        .birth(tester.getBirth())
//                        .phone(tester.getPhone())
//                        .build();
//
//        Set<ConstraintViolation<SellerSignUpForm>> violations =
//                validator.validate(sellerSignUpForm);
//        ConstraintViolation<?> constraintViolation = violations.stream().findFirst().get();
//
//        assertEquals(violations.size(), 1);
//        assertEquals(constraintViolation.getPropertyPath().toString(), testField);
//        assertEquals(constraintViolation.getMessage().toString(), testViolation);
//    }
//
//    @Test
//    @DisplayName("생일 필수 확인")
//    public void birth_not_null_test() throws Exception {
//        String testField = "birth";
//        String testViolation = "must not be null";
//
//        SellerSignUpForm sellerSignUpForm =
//                SellerSignUpForm.builder()
//                        .name(tester.getName())
//                        .email(tester.getEmail())
//                        .password(tester.getPassword())
//                        .phone(tester.getPhone())
//                        .build();
//
//        Set<ConstraintViolation<SellerSignUpForm>> violations =
//                validator.validate(sellerSignUpForm);
//        ConstraintViolation<?> constraintViolation = violations.stream().findFirst().get();
//
//        assertEquals(violations.size(), 1);
//        assertEquals(constraintViolation.getPropertyPath().toString(), testField);
//        assertEquals(constraintViolation.getMessage().toString(), testViolation);
//    }
//
//    @Test
//    @DisplayName("휴대폰 번호 필수 확인")
//    public void phone_not_blank_test() throws Exception {
//        String testField = "phone";
//        String testViolation = "must not be blank";
//
//        SellerSignUpForm sellerSignUpForm =
//                SellerSignUpForm.builder()
//                        .name(tester.getName())
//                        .email(tester.getEmail())
//                        .password(tester.getPassword())
//                        .birth(tester.getBirth())
//                        .build();
//
//        Set<ConstraintViolation<SellerSignUpForm>> violations =
//                validator.validate(sellerSignUpForm);
//        ConstraintViolation<?> constraintViolation = violations.stream().findFirst().get();
//
//        assertEquals(violations.size(), 1);
//        assertEquals(constraintViolation.getPropertyPath().toString(), testField);
//        assertEquals(constraintViolation.getMessage().toString(), testViolation);
//    }
//
//}