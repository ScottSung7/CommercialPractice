package com.example.accountapi.application.service.accountInfo.seller;

import com.example.accountapi.domain.model.Seller;
import com.example.accountapi.repository.seller.SellerRepository;
import com.example.accountapi.web.validation.exception.AccountException;
import com.example.accountapi.web.validation.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountInfoSellerServiceImpl implements AccountInfoSellerService {

    private final SellerRepository sellerRepository;
    @Override
    public Seller findSeller(String email) {
        return sellerRepository.findByEmail(email).orElseThrow(
                () -> new AccountException(ErrorCode.NOT_FOUND_USER)
        );
    }
}
