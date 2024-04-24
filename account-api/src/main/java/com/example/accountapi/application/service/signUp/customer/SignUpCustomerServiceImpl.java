package com.example.accountapi.application.service.signUp.customer;

import com.example.accountapi.domain.model.Customer;
import com.example.accountapi.repository.customer.CustomerRepository;
import com.example.accountapi.web.validation.exception.AccountException;
import com.example.accountapi.web.validation.form.customer.CustomerSignUpForm;
import com.example.accountapi.web.validation.form.customer.CustomerUpdateForm;
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
public class SignUpCustomerServiceImpl implements SignUpCustomerService{

    private final CustomerRepository customerRepository;


    private final PasswordEncoder customerPasswordEncoder;
    @Override
    @Transactional
    public Customer signUp(CustomerSignUpForm form){

        Customer customer = Customer.from(form);
        customer.encodePassword(customerPasswordEncoder.encode(form.getPassword()));
        if(customerRepository.findByEmail(customer.getEmail().toLowerCase(Locale.ROOT)).isPresent()){
            throw new AccountException(ErrorCode.ALREADY_REGISTER_USER);
        };

        Customer returned = customerRepository.save(customer);

        return returned;
    }
    @Override
    @Transactional
    public boolean customerVerify(String email){
        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(() -> new AccountException(ErrorCode.NOT_FOUND_USER));
        if(customer.isVerified()){
            throw new AccountException(ErrorCode.ALREADY_VERIFIED);
        }else if(customer.getVerificationExpiredAt().isBefore(LocalDateTime.now())){
            throw new AccountException(ErrorCode.VERIFICATION_EXPIRED);
        }
        return customer.verifyCustomer(true);
    }
    @Override
    @Transactional
    public Customer update(CustomerUpdateForm customerUpdateForm) {
        Customer customer = customerRepository.findByEmail(customerUpdateForm.getEmail())
                .orElseThrow(() -> new AccountException(ErrorCode.NOT_FOUND_USER));
        customer.encodePassword(customerPasswordEncoder.encode(customerUpdateForm.getPassword()));

        return  Customer.updateFrom(customerUpdateForm, customer);
    }

    @Override
    public boolean isEmailExist(String email) {
        return customerRepository.findByEmail(email.toLowerCase(Locale.ROOT)).isPresent();
    }

    @Override
    @Transactional
    public Customer addExpirationDate(Customer signUpCustomer, int num) {
        Customer customer = customerRepository.findById(signUpCustomer.getId())
                .orElseThrow(() -> new AccountException(ErrorCode.NOT_FOUND_USER));
        //하루 추가.
        customer.addVerificationExpirationDate(LocalDateTime.now().plusDays(num));
        return customer;
    }



}
