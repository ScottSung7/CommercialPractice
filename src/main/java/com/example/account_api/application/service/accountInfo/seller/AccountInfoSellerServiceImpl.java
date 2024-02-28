package com.example.account_api.application.service.accountInfo.seller;

import com.example.account_api.domain.model.Customer;
import com.example.account_api.domain.model.Seller;
import com.example.account_api.repository.customer.CustomerRepository;
import com.example.account_api.repository.seller.SellerRepository;
import com.example.account_api.web.validation.exception.AccountException;
import com.example.account_api.web.validation.exception.ErrorCode;
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
