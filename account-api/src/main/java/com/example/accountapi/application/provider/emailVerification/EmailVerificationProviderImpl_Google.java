package com.example.accountapi.application.provider.emailVerification;

import com.example.accountapi.application.provider.emailVerification.form.SendMailForm;
import com.example.accountapi.application.tools.crpto.AESCryptoUtil;
import com.example.accountapi.domain.model.Customer;
import com.example.accountapi.domain.model.Seller;
import com.example.accountapi.web.validation.exception.AccountException;
import com.example.accountapi.web.validation.exception.ErrorCode;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${email.server}")
    private String serverAddress;




    @Override
    public void sendVerificationEmail(Seller to) {

        try {
            SendMailForm sendMailForm = SendMailForm.builder()
                    .from(ADMIN_EMAIL)
                    .to(to.getEmail())
                    .subject(SUBJECT)
                    .text(getVerificationEmailBody(to.getEmail(), to.getName(), "seller"))
                    .build();
            boolean mailResult = createCertificationMail(sendMailForm.getTo(), sendMailForm.getSubject(), sendMailForm.getText());
            if(mailResult){
                log.info("Send email result : "+ to.getEmail()+"(SUCCESS)");
            }else{
                log.info("Send email result : "+ to.getEmail()+"(FAIL)");
                throw new AccountException(ErrorCode.VERIFICATION_EMAIL_ERROR);
            }
        }catch(Exception e){
            throw new AccountException(ErrorCode.VERIFICATION_EMAIL_ERROR);
        }
    }


    public void sendVerificationEmail(Customer to){
        try {
            SendMailForm sendMailForm = SendMailForm.builder()
                    .from(ADMIN_EMAIL)
                    .to(to.getEmail())
                    .subject(SUBJECT)
                    .text(getVerificationEmailBody(to.getEmail(), to.getName(), "customer"))
                    .build();

            boolean mailResult = createCertificationMail(sendMailForm.getTo(), sendMailForm.getSubject(), sendMailForm.getText());

            if (mailResult) {
                log.info("Send email result : " + to.getEmail() + "(SUCCESS)");
            } else {
                log.info("Send email result : " + to.getEmail() + "(FAIL)");
                throw new AccountException(ErrorCode.VERIFICATION_EMAIL_ERROR);
            }
        }catch(Exception e){
            System.out.println("email Creation");
            throw new AccountException(ErrorCode.VERIFICATION_EMAIL_ERROR);

        }
    }
    private boolean createCertificationMail(String to, String subject, String text){

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


    private String getVerificationEmailBody(String email, String name, String type) {

        try {
            //TODO: type 수정
            String certificationMessage = "";
            String encryptedEmail = AESCryptoUtil.encrypt(AESCryptoUtil.specName, AESCryptoUtil.KEY, email);

            String verifyingaddress = serverAddress+ "/accounts/" + type + "/verify/" + encryptedEmail;

//           String encryptedType = AESCryptoUtil.encrypt(AESCryptoUtil.specName, AESCryptoUtil.KEY, type);

            String link = "<a href='" + verifyingaddress +"'> 클릭하여 인증</a>";
            certificationMessage += "<h1 style='text-align:center;'>[인증메일]인증메일</h1>";
            certificationMessage += "<h3 style='text-align: center;'>인증링크: " + link + "</h3>";

            return certificationMessage;
        }catch (Exception e){
            System.out.println("emailBody");
            throw new AccountException(ErrorCode.VERIFICATION_EMAIL_ERROR);
        }
    }




}
