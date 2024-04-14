package com.example.accountapi.web.validation.form;


import com.example.accountapi.web.validation.form.customer.CustomerSignUpForm;
import com.example.accountapi.web.validation.form.customer.CustomerUpdateForm;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(MockitoExtension.class)
class CustomerUpdateForm_ValidationTest {

    private Validator validator;


    private CustomerUpdateForm form = new CustomerUpdateForm();
    @BeforeEach
    public void initEach() throws IllegalAccessException, NoSuchFieldException {
        validator = Validation.buildDefaultValidatorFactory().getValidator();

        // 리플렉션을 사용하여 private 필드에 접근하고 값을 설정합니다.
        Field field = CustomerSignUpForm.class.getDeclaredField("email");
        field.setAccessible(true);
        field.set(form, "tester@test.com");

        field = CustomerSignUpForm.class.getDeclaredField("name");
        field.setAccessible(true);
        field.set(form, "tester");

        field = CustomerSignUpForm.class.getDeclaredField("password");
        field.setAccessible(true);
        field.set(form, "1234");

        field = CustomerSignUpForm.class.getDeclaredField("birth");
        field.setAccessible(true);
        field.set(form, LocalDate.of(1990, 1, 1));

        field = CustomerSignUpForm.class.getDeclaredField("phone");
        field.setAccessible(true);
        field.set(form, "01000000000");
    }

//    @Test
//    @DisplayName("이메일 필수 확인")
//    public void email_not_blank_test() throws Exception {
//        String testField = "email";
//        String testViolation = "이메일을 입력해주세요";
//
//        // 리플렉션을 사용하여 private 필드에 접근하고 값을 설정합니다.
//        Field field = CustomerSignUpForm.class.getDeclaredField(testField);
//        field.setAccessible(true);
//        field.set(form, null);
//
//        Set<ConstraintViolation<CustomerSignUpForm>> violations = validator.validate(form);
//        ConstraintViolation<?> constraintViolation = violations.stream().findFirst().get();
//
//        assertEquals(1, violations.size());
//        assertEquals(constraintViolation.getPropertyPath().toString(), testField);
//        assertEquals(constraintViolation.getMessage().toString(), testViolation);
//    }

//    @Test
//    @DisplayName("이메일 필수 확인")
//    public void email_not_blank_test() throws Exception {
//        String testField = "email";
//        String testViolation = "이메일을 입력해주세요";
//
//        String email = "tester@test.com";
//        String name = "tester";
//        String password = "1234";
//        LocalDate birth = LocalDate.of(1990, 01, 01);
//        String phone = "01000000000";
//
//        when(formMock.getEmail()).thenReturn(null);
//        when(formMock.getName()).thenReturn("tester");
//        when(formMock.getPassword()).thenReturn(password);
//        when(formMock.getBirth()).thenReturn(LocalDate.of(1990, 01, 01));
//        when(formMock.getPhone()).thenReturn(phone);
//
//        System.out.println(formMock.getName());
//
//        Set<ConstraintViolation<CustomerSignUpForm>> violations =
//                validator.validate(formMock);
//        ConstraintViolation<?> constraintViolation = violations.stream().findFirst().get();
//        violations.stream().forEach(vi ->
//                {
//                    System.out.println(vi.getPropertyPath().toString());
//                    System.out.println(vi.getMessage().toString());
//                });
//
//        System.out.println(constraintViolation.getPropertyPath().toString());
//        System.out.println(constraintViolation.getMessage().toString());
//        assertEquals(1, violations.size());
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
//        CustomerSignUpForm customerSignUpForm =
//                CustomerSignUpForm.builder()
//                        .email(tester.getEmail())
//                        .password(tester.getPassword())
//                        .birth(tester.getBirth())
//                        .phone(tester.getPhone())
//                        .build();
//
//        Set<ConstraintViolation<CustomerSignUpForm>> violations =
//                validator.validate(customerSignUpForm);
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
//        CustomerSignUpForm customerSignUpForm =
//                CustomerSignUpForm.builder()
//                        .email(tester.getEmail())
//                        .name(tester.getName())
//                        .birth(tester.getBirth())
//                        .phone(tester.getPhone())
//                        .build();
//
//        Set<ConstraintViolation<CustomerSignUpForm>> violations =
//                validator.validate(customerSignUpForm);
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
//        CustomerSignUpForm customerSignUpForm =
//                CustomerSignUpForm.builder()
//                        .name(tester.getName())
//                        .email(tester.getEmail())
//                        .password(tester.getPassword())
//                        .phone(tester.getPhone())
//                        .build();
//
//        Set<ConstraintViolation<CustomerSignUpForm>> violations =
//                validator.validate(customerSignUpForm);
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
//        CustomerSignUpForm customerSignUpForm =
//                CustomerSignUpForm.builder()
//                        .name(tester.getName())
//                        .email(tester.getEmail())
//                        .password(tester.getPassword())
//                        .birth(tester.getBirth())
//                        .build();
//
//        Set<ConstraintViolation<CustomerSignUpForm>> violations =
//                validator.validate(customerSignUpForm);
//        ConstraintViolation<?> constraintViolation = violations.stream().findFirst().get();
//
//        assertEquals(violations.size(), 1);
//        assertEquals(constraintViolation.getPropertyPath().toString(), testField);
//        assertEquals(constraintViolation.getMessage().toString(), testViolation);
}
