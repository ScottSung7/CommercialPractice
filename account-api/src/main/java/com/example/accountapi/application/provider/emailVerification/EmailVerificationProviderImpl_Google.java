package com.example.accountapi.application.provider.emailVerification;

import com.example.accountapi.application.provider.emailVerification.form.SendMailForm;
import com.example.accountapi.domain.model.Customer;
import com.example.accountapi.domain.model.Seller;
import com.example.accountapi.web.validation.exception.AccountException;
import com.example.accountapi.web.validation.exception.ErrorCode;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailVerificationProviderImpl_Google implements EmailVerificationProvider{

    private final JavaMailSender javaMailSender;
    private final String SUBJECT = "[서비스 인증메일 입니다] 인증 확인 부탁 드립니다.";
    private String ADMIN_EMAIL = "hgssung@gmail.com";

    @Override
    public String sendVerificationEmail(Seller to){
        String code = getRandomCode();
        SendMailForm sendMailForm = SendMailForm.builder()
                .from(ADMIN_EMAIL)
                .to(to.getEmail())
                .subject(SUBJECT)
                .code(code)
                .text(getVerificationEmailBody(to.getEmail(), to.getName() , code))
                .build();
        if(createCertificationMail(sendMailForm.getTo(), sendMailForm.getSubject(), sendMailForm.getText())){
            log.info("Send email result : "+ to.getEmail()+"(SUCCESS)");
            return code;
        }else{
            log.info("Send email result : "+ to.getEmail()+"(FAIL)");
            throw new AccountException(ErrorCode.VERIFICATION_EMAIL_ERROR);
        }
    }

    @Override
    public String sendVerificationEmail(Customer to){
       String code = getRandomCode();
        SendMailForm sendMailForm = SendMailForm.builder()
                .from(ADMIN_EMAIL)
                .to(to.getEmail())
                .subject(SUBJECT)
                .code(code)
                .text(getVerificationEmailBody(to.getEmail(), to.getName() , code))
                .build();
        if(createCertificationMail(sendMailForm.getTo(), sendMailForm.getSubject(), sendMailForm.getText())){
            log.info("Send email result : "+ to.getEmail()+"(SUCCESS)");
            return code;
        }else{
            log.info("Send email result : "+ to.getEmail()+"(FAIL)");
            throw new AccountException(ErrorCode.VERIFICATION_EMAIL_ERROR);
        }
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
        return "ABC";
    }

    private String getVerificationEmailBody(String email, String name, String code) {
        String certificationMessage = "";
        String verifyingaddress = "http://localhost:8080/accounts/customer/verify/";

        String link = "<a href='"+verifyingaddress+email+"'> 클릭하여 인증</a>";
        certificationMessage += "<h1 style='text-align:center;'>[인증메일]인증메일</h1>";
        certificationMessage += "<h3 style='text-align: center;'>인증코드: "+ code+"</h3>";
        certificationMessage += "<h3 style='text-align: center;'>인증링크: "+ link+"</h3>";



        return certificationMessage;
    }



}
