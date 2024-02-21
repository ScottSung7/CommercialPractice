package com.example.account_api.application.provider.emailVerification;

import com.example.account_api.application.provider.emailVerification.form.SendMailForm;
import com.example.account_api.domain.model.Customer;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailVerificationProviderImpl_Google implements EmailVerificationProvider{

    private final JavaMailSender javaMailSender;
    private final String SUBJECT = "[서비스 인증메일 입니다] 인증 확인 부탁 드립니다.";

    public Optional<SendMailForm> sendVerificationEmail(String fromEmail, Customer to){
       String code = getRandomCode();
        SendMailForm sendMailForm = SendMailForm.builder()
                .from(fromEmail)
                .to(to.getEmail())
                .subject(SUBJECT)
                .code(code)
                .text(getVerificationEmailBody(to.getEmail(), to.getName() , code))
                .build();
        return createCertificationMail(sendMailForm.getTo(), sendMailForm.getSubject(), sendMailForm.getText())
                    ? Optional.of(sendMailForm) : Optional.ofNullable(null);
    }
    public boolean createCertificationMail(String to, String subject, String text){

        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);

            messageHelper.setTo(to);
            messageHelper.setSubject(subject);
            messageHelper.setText(text, true);

            javaMailSender.send(message);
            return true;

        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    private String getRandomCode() {
        return "";
    }

    private String getVerificationEmailBody(String email, String name, String code) {
        String certificationMessage = "";
        certificationMessage += "<h1 style='text-align:center;'>[인증메일]인증메일</h1>";
        certificationMessage += "<h3 style='text-align: center;'>내용</h3>";
        return certificationMessage;
    }



}
