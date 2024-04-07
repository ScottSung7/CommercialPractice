package com.example.accountapi.application.service;

import com.example.accountapi.domain.model.Customer;
import com.example.accountapi.domain.model.CustomerBalanceHistory;
import com.example.accountapi.repository.customer.CustomerBalanceHistoryRepository;
import com.example.accountapi.repository.customer.CustomerRepository;
import com.example.accountapi.web.validation.exception.AccountException;
import com.example.accountapi.web.validation.exception.ErrorCode;
import com.example.accountapi.web.validation.form.ChangeBalanceForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomerBalanceService {

    private final CustomerBalanceHistoryRepository customerBalanceHistoryRepository;
    private final CustomerRepository customerRepository;

    @Transactional(noRollbackFor = {AccountException.class})
    public CustomerBalanceHistory changeBalance(String email, ChangeBalanceForm form) throws AccountException {
        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(() -> new AccountException(ErrorCode.NOT_FOUND_USER));

        //초기화 또는 현 상태.
        CustomerBalanceHistory customerBalanceHistory = getCurrentStatus(customer);

        if(customerBalanceHistory.getCurrentMoney() + form.getMoney() < 0){
            throw new AccountException(ErrorCode.NOT_ENOUGH_BALANCE);
        }

        //balance 변경됨.
        customerBalanceHistory = CustomerBalanceHistory.builder()
                .changedMoney(form.getMoney())
                .currentMoney(customerBalanceHistory.getCurrentMoney() + form.getMoney())
                .description(form.getMessage())
                .fromMessage(form.getFrom())
                .customer(customerBalanceHistory.getCustomer())
                .build();
        customerBalanceHistory.getCustomer().setBalance(customerBalanceHistory.getChangedMoney());

        return customerBalanceHistoryRepository.save(customerBalanceHistory);

    }
    public CustomerBalanceHistory checkBalance(String email) throws AccountException{
        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(() -> new AccountException(ErrorCode.NOT_FOUND_USER));
        return getCurrentStatus(customer);
    }

    private CustomerBalanceHistory getCurrentStatus(Customer customer){
        return customerBalanceHistoryRepository.findFirstByCustomer_IdOrderByIdDesc(customer.getId())
                        .orElse(CustomerBalanceHistory.builder()
                                .changedMoney(0)
                                .currentMoney(0)
                                .customer(customer)
                                .build());
    }




}
