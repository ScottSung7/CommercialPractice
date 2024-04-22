package com.example.accountapi.web.controller.account;


import com.example.accountapi.application.applications.AccountInfo.AccountInfoApplication;
import com.example.accountapi.domain.dto.CustomerDto;
import com.example.accountapi.domain.dto.SellerDto;
import com.example.accountapi.domain.dto.UserDto;
import com.example.accountapi.domain.model.Customer;
import com.example.accountapi.domain.model.Seller;
import com.example.accountapi.config.SpringSecurity.LoginCheck;
import com.example.accountapi.config.SpringSecurity.id.customer.CustomerPrincipalDetails;
import com.example.accountapi.config.SpringSecurity.id.seller.SellerPrincipalDetails;
import com.example.accountapi.web.validation.exception.AccountException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static com.example.accountapi.web.controller.account.AccountAPIControllerProperties.ACCOUNT_COMMON_URL;

@RestController
@RequestMapping(ACCOUNT_COMMON_URL)
@RequiredArgsConstructor
@Slf4j
public class AccountInfoController {

    private final AccountInfoApplication accountInfoApplication;

    @PostMapping("/customer")
    public ResponseEntity<CustomerDto> getCustomerInfo(Authentication authentication) {

        CustomerPrincipalDetails customerDetails = LoginCheck.customerCheck(authentication);
        Customer customer = accountInfoApplication.findCustomer(customerDetails.getEmail());

        return ResponseEntity.ok(CustomerDto.from(customer));
    }
    @PostMapping("/customer/info")
    public ResponseEntity<CustomerDto> getCustomerInfo(@RequestParam("email") String email) {
        Customer customer = accountInfoApplication.findCustomer(email);
        return ResponseEntity.ok(CustomerDto.from(customer));
    }

    @PostMapping("/seller/info")
    public ResponseEntity<SellerDto> getSellerInfo(@RequestParam("email") String email) {
        Seller seller = accountInfoApplication.findSeller(email);
        return ResponseEntity.ok(SellerDto.from(seller));
    }
    //Seller는 표시 다르게.
    @PostMapping("/user/info")
    public ResponseEntity<List<UserDto>> getUserInfo(@RequestParam("email") String email) {
        List<UserDto> userList = new ArrayList<UserDto>();
        try{
            Customer customer = accountInfoApplication.findCustomer(email);
            UserDto customerDto = UserDto.from(customer);
            userList.add(customerDto);
        }catch(AccountException e){
            log.trace("Customer에는 존재하는 이메일이 아닙니다.");
        }

        try{
            Seller seller = accountInfoApplication.findSeller(email);
            UserDto sellerDto = UserDto.from(seller);
            userList.add(sellerDto);
        }catch(AccountException e){
            log.trace("Seller에는 존재하는 이메일이 아닙니다.");
        }

        return ResponseEntity.ok(userList);
    }
    
}
