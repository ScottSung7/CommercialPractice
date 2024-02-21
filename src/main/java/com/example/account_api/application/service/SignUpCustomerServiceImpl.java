package com.example.account_api.application.service;

import com.example.account_api.application.tools.LogAndException;
import com.example.account_api.domain.model.Customer;
import com.example.account_api.repository.customer.CustomerRepository;
import com.example.account_api.web.validation.form.SignUpCustomerForm;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SignUpCustomerServiceImpl implements SignUpCustomerService{

    private final CustomerRepository customerRepository;
    private final LogAndException logAndException;

    public Customer signUp(SignUpCustomerForm form){
        return customerRepository.save(Customer.from(form));
    }

    @Override
    public Optional<Customer> isEmailExist(String email) {
        return customerRepository.findByEmail(email);
    }

    @Override
    @Transactional
    public LocalDateTime changeCustomerValidateEmail(Long customerId, String verificationCode) {
        Optional<Customer> customerOptional = customerRepository.findById(customerId);

        logAndException.notFoundUserCheck(customerOptional.isPresent());

        Customer customer = customerOptional.get();
        customer.setVerificationCode(verificationCode);
        customer.setVerificationExpiredAt(LocalDateTime.now().now().plusDays(1));
        return customer.getVerificationExpiredAt();

    }

}
