package com.example.accountapi.application.service.signIn.seller;

import com.example.accountapi.domain.model.Seller;
import com.example.accountapi.repository.seller.SellerRepository;
import com.example.accountapi.web.validation.exception.AccountException;
import com.example.accountapi.web.validation.form.seller.SignUpSellerForm;
import com.example.accountapi.web.validation.exception.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Locale;

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
    public Seller changeSellerValidateEmail(Seller signUpSeller) {
         Seller seller = sellerRepository.findById(signUpSeller.getId())
                .orElseThrow(() -> new AccountException(ErrorCode.NOT_FOUND_USER));

        seller.setVerificationExpiredAt(LocalDateTime.now().now().plusDays(1));

        return seller;

    }
    @Override
    @Transactional
    public void sellerVerify(String email) {
        Seller seller = sellerRepository.findByEmail(email)
                .orElseThrow(() -> new AccountException(ErrorCode.NOT_FOUND_USER));
        if(seller.isVerified()){
            throw new AccountException(ErrorCode.ALREADY_VERIFIED);
        }else if(seller.getVerificationExpiredAt().isBefore(LocalDateTime.now())){
            throw new AccountException(ErrorCode.VERIFICATION_EXPIRED);
        }
        seller.setVerified(true);
    }

}
