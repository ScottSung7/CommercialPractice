package com.example.config.SpringSecurity;

import com.example.account_api.domain.model.Customer;
import com.example.account_api.repository.customer.CustomerRepository;
import com.example.account_api.web.validation.exception.AccountException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static com.example.account_api.web.validation.exception.ErrorCode.NOT_FOUND_USER;

@Service
public class CustomerLogInService_SpringSecurity implements UserDetailsService {

    @Autowired
    CustomerRepository customerRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Customer customer = customerRepository.findByEmail(email).orElseThrow(
                () -> new AccountException(NOT_FOUND_USER)
        );
        System.out.println("Customer Login");
       // System.out.println(customer.getName());

        return new CustomerPrincipalDetails(customer);
    }

}
