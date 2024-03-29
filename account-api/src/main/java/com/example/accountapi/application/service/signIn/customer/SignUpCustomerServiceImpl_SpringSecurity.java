package com.example.accountapi.application.service.signIn.customer;

import com.example.accountapi.domain.model.Customer;
import com.example.accountapi.repository.customer.CustomerRepository;
import com.example.accountapi.web.validation.exception.AccountException;
import com.example.accountapi.web.validation.form.customer.SignUpCustomerForm;
import com.example.accountapi.web.validation.form.customer.UpdateCustomerForm;
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
public class SignUpCustomerServiceImpl_SpringSecurity implements SignUpCustomerService{

    private final CustomerRepository customerRepository;
    @Autowired
    @Qualifier("customerPasswordEncoder")
    private final PasswordEncoder customerPasswordEncoder;

    public Customer signUp(SignUpCustomerForm form){
        Customer customer = Customer.from(form);
        customer.setPassword(customerPasswordEncoder.encode(form.getPassword()));

        return customerRepository.save(customer);
    }
    @Transactional
    public void customerVerify(String email){
        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(() -> new AccountException(ErrorCode.NOT_FOUND_USER));
        if(customer.isVerified()){
            throw new AccountException(ErrorCode.ALREADY_VERIFIED);
        }else if(customer.getVerificationExpiredAt().isBefore(LocalDateTime.now())){
            throw new AccountException(ErrorCode.VERIFICATION_EXPIRED);
        }
        customer.setVerified(true);

    }
    @Transactional
    public Customer update(UpdateCustomerForm updateCustomerForm) {
        Customer customer = customerRepository.findByEmail(updateCustomerForm.getEmail())
                .orElseThrow(() -> new AccountException(ErrorCode.NOT_FOUND_USER));
        customer.setPassword(customerPasswordEncoder.encode(updateCustomerForm.getPassword()));

        return  Customer.updateFrom(updateCustomerForm, customer);
    }

    @Override
    public boolean isEmailExist(String email) {
        return customerRepository.findByEmail(email.toLowerCase(Locale.ROOT)).isPresent();
    }

    @Override
    @Transactional
    public Customer changeCustomerValidateEmail(Customer signUpCustomer) {
        Customer customer = customerRepository.findById(signUpCustomer.getId())
                .orElseThrow(() -> new AccountException(ErrorCode.NOT_FOUND_USER));

        customer.setVerificationExpiredAt(LocalDateTime.now().now().plusDays(1));

        return customer;
    }


}
