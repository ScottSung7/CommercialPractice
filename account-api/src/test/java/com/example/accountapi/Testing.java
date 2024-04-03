package com.example.accountapi;

import com.example.accountapi.application.tools.crpto.AESCryptoUtil;
import jakarta.transaction.Transactional;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;




public class Testing {



    private static String encryptedText;
    @Test
    public void test() throws Exception{
        String plainText = "Hello, MadPlay!";

        encryptedText = AESCryptoUtil.encrypt(AESCryptoUtil.specName, AESCryptoUtil.KEY, plainText);


        System.out.println("cipherText: " + encryptedText);

    }

    @Test
    @Transactional
    public void test2() throws Exception {

        String decryptedText = AESCryptoUtil.decrypt(AESCryptoUtil.specName, AESCryptoUtil.KEY,encryptedText);
        System.out.println("plainText: " + decryptedText);
    }
}
