package com.example.orderapi.config.SpringSecurity;

import com.example.orderapi.config.common.SpringSecurity.id.User;
import com.example.orderapi.config.common.SpringSecurity.id.UserPrincipalDetails;
import com.example.orderapi.config.common.validation.LoginCheckMSA;
import com.example.orderapi.config.common.validation.accountException.AccountException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;

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
        assertDoesNotThrow(() -> LoginCheckMSA.notLogin(null));
        when(authentication.isAuthenticated()).thenReturn(true);
        assertThrows(AccountException.class, () -> LoginCheckMSA.notLogin(authentication));
    }

    @Mock
    User user;
    @Test
    public void testSellerCheck() {
        when(user.getType()).thenReturn("SELLER");
        when(authentication.getPrincipal()).thenReturn(new UserPrincipalDetails(user));

        UserPrincipalDetails sellerPrincipalDetails = LoginCheckMSA.sellerCheck(authentication);
        assertEquals("SELLER", sellerPrincipalDetails.getType());
    }

    @Test
    public void testCustomerCheck_() {
        when(user.getType()).thenReturn("CUSTOMER");
        when(authentication.getPrincipal()).thenReturn(new UserPrincipalDetails(user));
        UserPrincipalDetails customerPrincipalDetails = LoginCheckMSA.customerCheck(authentication);
        assertEquals("CUSTOMER", customerPrincipalDetails.getType());
    }

    @Test
    public void testCustomerCheckType() {
        assertThrows(AccountException.class, () -> LoginCheckMSA.customerCheck("seller"));
        assertDoesNotThrow(() -> LoginCheckMSA.customerCheck("customer"));
    }

    @Test
    public void testSellerCheckType() {
        assertThrows(AccountException.class, () -> LoginCheckMSA.sellerCheck("customer"));
        assertDoesNotThrow(() -> LoginCheckMSA.sellerCheck("seller"));
    }

}