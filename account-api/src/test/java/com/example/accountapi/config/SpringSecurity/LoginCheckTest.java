package com.example.accountapi.config.SpringSecurity;

import com.example.accountapi.config.SpringSecurity.id.customer.CustomerPrincipalDetails;
import com.example.accountapi.config.SpringSecurity.id.seller.SellerPrincipalDetails;
import com.example.accountapi.web.validation.exception.AccountException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class LoginCheckTest {

    @Mock
    private Authentication authentication;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
     void testNotLogin() {
        assertDoesNotThrow(() -> LoginCheck.notLogin(null));
        when(authentication.isAuthenticated()).thenReturn(true);
        assertThrows(AccountException.class, () -> LoginCheck.notLogin(authentication));
    }

    @Test
    public void testSellerCheck() {
        when(authentication.getPrincipal()).thenReturn(new SellerPrincipalDetails(null));
        SellerPrincipalDetails sellerPrincipalDetails = LoginCheck.sellerCheck(authentication);
        assertTrue(sellerPrincipalDetails instanceof SellerPrincipalDetails);
    }

    @Test
    public void testCustomerCheck_() {
        when(authentication.getPrincipal()).thenReturn(new CustomerPrincipalDetails(null));
        CustomerPrincipalDetails customerPrincipalDetails = LoginCheck.customerCheck(authentication);
        assertTrue(customerPrincipalDetails instanceof CustomerPrincipalDetails);
    }

    @Test
    public void testCustomerCheckType() {
        assertThrows(AccountException.class, () -> LoginCheck.customerCheck("seller"));
        assertDoesNotThrow(() -> LoginCheck.customerCheck("customer"));
    }

    @Test
    public void testSellerCheckType() {
        assertThrows(AccountException.class, () -> LoginCheck.sellerCheck("customer"));
        assertDoesNotThrow(() -> LoginCheck.sellerCheck("seller"));
    }

}