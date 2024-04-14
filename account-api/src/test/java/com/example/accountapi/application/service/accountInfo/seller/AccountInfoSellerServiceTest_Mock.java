package com.example.accountapi.application.service.accountInfo.seller;

import com.example.accountapi.TestConfiguration_Service_Mock;
import com.example.accountapi.domain.model.Seller;
import com.example.accountapi.repository.seller.SellerRepository;
import com.example.accountapi.web.validation.exception.AccountException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static com.example.accountapi.web.validation.exception.ErrorCode.NOT_FOUND_USER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {TestConfiguration_Service_Mock.class})
@ExtendWith(MockitoExtension.class)
class AccountInfoSellerServiceTest_Mock {

    String email = "tester@test.com";

    @Autowired
    private AccountInfoSellerService accountInfoSellerService;

    @Autowired
    private SellerRepository sellerRepository;

    @Test
    void findSeller() {
        //given
        Seller seller = Seller.builder()
                .email(email)
                .name("tester")
                .build();
        //when
        when(sellerRepository.findByEmail(email)).thenReturn(Optional.of(seller));
        Seller sellerReturned = accountInfoSellerService.findSeller(email);

        //then
        assertEquals(sellerReturned.getEmail(), email);
        assertEquals(sellerReturned.getName(), "tester");
    }

    @Test
    void findSeller_NOT_FOUND(){
        //given
        String email = "";
        //when
        AccountException exception
                = assertThrows(AccountException.class, () -> accountInfoSellerService.findSeller(email));
        //then
        assertEquals(NOT_FOUND_USER, exception.getErrorCode());
    }
}