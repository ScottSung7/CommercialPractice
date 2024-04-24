package com.example.accountapi.application.applications.AccountInfo;

import com.example.accountapi.TestConfiguration_Service_Mock;
import com.example.accountapi.domain.model.Customer;
import com.example.accountapi.domain.model.Seller;
import com.example.accountapi.repository.customer.CustomerRepository;
import com.example.accountapi.repository.seller.SellerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;


@SpringBootTest(classes = {TestConfiguration_Service_Mock.class})
@ExtendWith(MockitoExtension.class)
class AccountInfoApplicationTest_Mock {

    @Autowired
    AccountInfoApplication accountInfoApplication;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    SellerRepository sellerRepository;;

    @Test
    void findCustomer() {
        //given
        String email = "tester@test.com";
        Customer customer = Customer.builder().
                email(email).
                name("tester").
                phone("01000000000").
                birth(LocalDate.now()).
                build();
        //when
        given(customerRepository.findByEmail(email)).willReturn(Optional.of(customer));
        Customer customerReturned = accountInfoApplication.findCustomer(email);

        //then
        assertTrue(customerReturned.getEmail().equals(email));
        assertTrue(customerReturned.getName().equals("tester"));
    }

    @Test
    void findSeller() {
        //given
        String email = "tester@test.com";
        Seller seller = Seller.builder()
                .email(email)
                .name("tester")
                .build();
        //when
        when(sellerRepository.findByEmail(email)).thenReturn(Optional.of(seller));
        Seller sellerReturned = accountInfoApplication.findSeller(email);

        //then
        assertEquals(sellerReturned.getEmail(), email);
        assertEquals(sellerReturned.getName(), "tester");
    }
}