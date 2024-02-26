package com.example.account_api.application.service.logIn;

import com.example.account_api.domain.model.Customer;
import com.example.account_api.domain.model.springSecurity.PrincipalDetails;
import com.example.account_api.repository.customer.CustomerRepository;
import com.example.account_api.web.validation.exception.AccountException;
import com.example.account_api.web.validation.exception.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LogInService_SpringSecurity implements UserDetailsService {

    @Autowired
    CustomerRepository customerRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Customer customer = customerRepository.findByEmail(email).orElseThrow(
                () -> new AccountException(ErrorCode.NOT_FOUND_USER)
        );
        System.out.println(customer.getName());

        return new PrincipalDetails(customer);
    }
}
