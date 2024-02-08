package com.example.service;

import com.example.client.MailgunClient;
import com.example.client.mailgun.SendMailForm;
import feign.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailSendService {

    private final MailgunClient mailgunClient;

    public ResponseEntity<Response> sendMail(){
        SendMailForm form = SendMailForm.builder()
                .from("sungscott@naver.com")
                .to("hgssung@gmail.com")
                .subject("Test email from zero base")
                .text("my text")
                .build();

        return mailgunClient.sendEmail(form);
    }



    public void test(){
        System.out.println("test");
    }

}
