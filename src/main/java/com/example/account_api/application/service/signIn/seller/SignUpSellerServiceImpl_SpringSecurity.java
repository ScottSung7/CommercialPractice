package com.example.account_api.application.service.signIn.seller;

import com.example.account_api.domain.model.Seller;
import com.example.account_api.domain.model.Seller;
import com.example.account_api.repository.seller.SellerRepository;
import com.example.account_api.web.validation.exception.AccountException;
import com.example.account_api.web.validation.form.seller.SignUpSellerForm;
import com.example.account_api.web.validation.form.seller.SignUpSellerForm;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Locale;

import static com.example.account_api.web.validation.exception.ErrorCode.NOT_FOUND_USER;

@Service
@Primary
@RequiredArgsConstructor
public class SignUpSellerServiceImpl_SpringSecurity implements SignUpSellerService {

    private final SellerRepository sellerRepository;

    @Autowired
    @Qualifier("sellerPasswordEncoder")
    private final PasswordEncoder sellerPasswordEncoder;

    public Seller signUp(SignUpSellerForm form){
        Seller seller = Seller.from(form);
        seller.setPassword(sellerPasswordEncoder.encode(form.getPassword()));

        return sellerRepository.save(seller);
    }
    @Override
    public boolean isEmailExist(String email) {
        return sellerRepository.findByEmail(email.toLowerCase(Locale.ROOT)).isPresent();
    }

    @Override
    @Transactional
    public Seller changeSellerValidateEmail(Seller signUpSeller, String verificationCode) {
         Seller seller = sellerRepository.findById(signUpSeller.getId())
                .orElseThrow(() -> new AccountException(NOT_FOUND_USER));

        seller.setVerificationCode(verificationCode);
        seller.setVerificationExpiredAt(LocalDateTime.now().now().plusDays(1));

        return seller;

    }

}