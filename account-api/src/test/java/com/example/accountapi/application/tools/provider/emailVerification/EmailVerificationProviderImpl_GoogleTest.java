package com.example.accountapi.application.tools.provider.emailVerification;

import com.example.accountapi.domain.model.Customer;
import com.example.accountapi.domain.model.Seller;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.javamail.JavaMailSender;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

class EmailVerificationProviderImpl_GoogleTest {


    @Mock
    private JavaMailSender javaMailSender;

    private EmailVerificationProviderImpl_Google emailVerificationProvider;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        emailVerificationProvider = new EmailVerificationProviderImpl_Google(javaMailSender);
    }

    @Test
    public void testSendVerificationEmailToSeller() throws Exception {
        Seller seller = Seller.builder().email("tester@test.com").build();

        MimeMessage mimeMessage = mock(MimeMessage.class);
        when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);

        boolean check = emailVerificationProvider.sendVerificationEmail(seller);

        verify(javaMailSender, times(1)).send(any(MimeMessage.class));
        assertTrue(check);
    }

    @Test
    public void testSendVerificationEmailToCustomer() throws Exception {
        Customer customer = Customer.builder().email("test@test.com").build();

        MimeMessage mimeMessage = mock(MimeMessage.class);
        when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);

        boolean check = emailVerificationProvider.sendVerificationEmail(customer);

        verify(javaMailSender, times(1)).send(any(MimeMessage.class));
        assertTrue(check);
    }
}