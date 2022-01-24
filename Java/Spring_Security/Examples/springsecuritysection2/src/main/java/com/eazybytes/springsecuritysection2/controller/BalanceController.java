package com.eazybytes.springsecuritysection2.controller;

import com.eazybytes.springsecuritysection2.entity.AccountTransactions;
import com.eazybytes.springsecuritysection2.entity.Customer;
import com.eazybytes.springsecuritysection2.repository.AccountsTransactionsRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BalanceController {

  private final AccountsTransactionsRepository accountsTransactionsRepository;

  // This is not best practice concerning api design -> the course used it like this
  @PostMapping("/myBalance")
  public List<AccountTransactions> getBalanceDetails(@RequestBody Customer customer) {

    return this.accountsTransactionsRepository.findByCustomerIdOrderByTransactionDtDesc(
        customer.getId()
    );
  }

}
