package com.example.config.SpringSecurity;

import com.example.account_api.domain.model.Seller;
import com.example.account_api.repository.seller.SellerRepository;
import com.example.account_api.web.validation.exception.AccountException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static com.example.account_api.web.validation.exception.ErrorCode.NOT_FOUND_USER;

@Service
public class SellerLogInService_SpringSecurity implements UserDetailsService {

    @Autowired
    SellerRepository sellerRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Seller seller = sellerRepository.findByEmail(email).orElseThrow(
                () -> new AccountException(NOT_FOUND_USER)
        );
        System.out.println("seller Login");
        //System.out.println(seller.getName());

        return new SellerPrincipalDetails(seller);
    }

}
