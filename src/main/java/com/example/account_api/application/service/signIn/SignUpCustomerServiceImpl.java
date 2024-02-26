package com.example.account_api.application.service.signIn;

import com.example.account_api.domain.model.Customer;
import com.example.account_api.repository.customer.CustomerRepository;
import com.example.account_api.web.validation.exception.AccountException;
import com.example.account_api.web.validation.form.customer.SignUpCustomerForm;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Locale;

import static com.example.account_api.web.validation.exception.ErrorCode.NOT_FOUND_USER;

@Service
@RequiredArgsConstructor
public class SignUpCustomerServiceImpl implements SignUpCustomerService{

    private final CustomerRepository customerRepository;


    public Customer signUp(SignUpCustomerForm form){


        return customerRepository.save(Customer.from(form));
    }

    @Override
    public boolean isEmailExist(String email) {
        return customerRepository.findByEmail(email.toLowerCase(Locale.ROOT)).isPresent();
    }

    @Override
    @Transactional
    public Customer changeCustomerValidateEmail(Customer signUpCustomer, String verificationCode) {
        Customer customer = customerRepository.findById(signUpCustomer.getId())
                .orElseThrow(() -> new AccountException(NOT_FOUND_USER));

        customer.setVerificationCode(verificationCode);
        customer.setVerificationExpiredAt(LocalDateTime.now().now().plusDays(1));

        return customer;

    }

}
