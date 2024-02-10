package com.example.service;

import com.example.domain.SignUpForm;
import com.example.domain.model.Customer;
import com.example.excption.CustomerException;
import com.example.repository.CustomerRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Optional;

import static com.example.excption.ErrorCode.NOT_FOUND_USER;

@Service
@RequiredArgsConstructor
public class SignUpCustomerService {

    private final CustomerRepository customerRepository;

    public Customer signUp(SignUpForm form){
        return customerRepository.save(Customer.from(form));
    };

    public boolean isEmailExists(String email){
        return customerRepository.findByEmail(email.toLowerCase(Locale.ROOT))
                .isPresent();
    }

    @Transactional
    public LocalDateTime changeCustomerVerficationEmail(Long customerId, String verificationCode){
        Optional<Customer> customerOptional = customerRepository.findById(customerId);

        if(customerOptional.isPresent()){
            Customer customer = customerOptional.get();
            customer.setVerficationCode(verificationCode);
            customer.setVerifyExpiredAt(LocalDateTime.now());
            return customer.getVerifyExpiredAt();
        }
        throw new CustomerException(NOT_FOUND_USER);


    }
}
