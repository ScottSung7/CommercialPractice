package com.example.account_api.application.service;

import com.example.account_api.domain.model.Customer;
import com.example.account_api.domain.model.CustomerBalanceHistory;
import com.example.account_api.repository.customer.CustomerBalanceHistoryRepository;
import com.example.account_api.repository.customer.CustomerRepository;
import com.example.account_api.web.validation.exception.AccountException;
import com.example.account_api.web.validation.exception.ErrorCode;
import com.example.account_api.web.validation.form.ChangeBalanceForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.account_api.web.validation.exception.ErrorCode.NOT_ENOUGH_BALANCE;

@Service
@RequiredArgsConstructor
public class CustomerBalanceService {

    private final CustomerBalanceHistoryRepository customerBalanceHIstoryRepository;
    private final CustomerRepository customerRepository;

    @Transactional(noRollbackFor = {AccountException.class})
    public CustomerBalanceHistory changeBalance(String email, ChangeBalanceForm form) throws AccountException {
        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(() -> new AccountException(ErrorCode.NOT_FOUND_USER));

        CustomerBalanceHistory customerBalanceHistory =
                customerBalanceHIstoryRepository.findFirstByCustomer_IdOrderByIdDesc(customer.getId())
                        .orElse(CustomerBalanceHistory.builder()
                                .changedMoney(0)
                                .currentMoney(0)
                                .customer(customer)
                                .build());
        if(customerBalanceHistory.getCurrentMoney() + form.getMoney() < 0){
            throw new AccountException(NOT_ENOUGH_BALANCE);
        }

        customerBalanceHistory = CustomerBalanceHistory.builder()
                .changedMoney(form.getMoney())
                .currentMoney(customerBalanceHistory.getCurrentMoney() + form.getMoney())
                .description(form.getMessage())
                .fromMessage(form.getFrom())
                .customer(customerBalanceHistory.getCustomer())
                .build();
        customerBalanceHistory.getCustomer().setBalance(customerBalanceHistory.getChangedMoney());

        return customerBalanceHIstoryRepository.save(customerBalanceHistory);



    }


}
