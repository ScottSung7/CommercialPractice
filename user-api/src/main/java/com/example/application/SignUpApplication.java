package com.example.application;

import com.example.client.MailgunClient;
import com.example.client.mailgun.SendMailForm;
import com.example.domain.SignUpForm;
import com.example.domain.model.Customer;
import com.example.excption.CustomerException;
import com.example.excption.ErrorCode;
import com.example.repository.CustomerRepository;
import com.example.service.SignUpCustomerService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class SignUpApplication {

    private final MailgunClient mailgunClient;
    private final SignUpCustomerService signUpCustomerService;

    public String customerSignUp(SignUpForm form) {
        if (signUpCustomerService.isEmailExists(form.getEmail())) {
            throw new CustomerException(ErrorCode.ALREADY_REGISTER_USER);
        } else {
            Customer c = signUpCustomerService.signUp(form);
            LocalDateTime now = LocalDateTime.now();
            String code = getRandomCode();


            SendMailForm sendMailForm = SendMailForm.builder()
                    .from("tester@dannymytester.com")
                    .to("hgsung@gmail.com")
                    .subject("Verficiation Email")
                    .text(getVerificationEmailBody(form.getEmail(), form.getName(), code))
                    .build();

            mailgunClient.sendEmail(sendMailForm);
            signUpCustomerService.changeCustomerVerficationEmail(c.getId(), code);
           return "회원가입에 성공하였습니다.";
        }
    }

    private String getRandomCode() {
        return RandomStringUtils.random(10, true, true);
    }

    private String getVerificationEmailBody(String email, String name, String code) {
        StringBuilder builder = new StringBuilder();
        return builder.append("Hello").append(name).append("! Please Click Link for verification.\n\n")
                .append("http://localhost:8080/custoemr/signup/verify?email=")
                .append(email)
                .append("&code=")
                .append(code).toString();
    }


}
