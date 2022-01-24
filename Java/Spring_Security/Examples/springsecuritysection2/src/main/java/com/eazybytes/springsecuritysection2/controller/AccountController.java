package com.eazybytes.springsecuritysection2.controller;

import com.eazybytes.springsecuritysection2.entity.Accounts;
import com.eazybytes.springsecuritysection2.entity.Customer;
import com.eazybytes.springsecuritysection2.repository.AccountsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AccountController {

  private final AccountsRepository accountsRepository;

  // This is not best practice concerning api design -> the course used it like this
  @PostMapping("/myAccount")
  public Accounts getAccountDetails(@RequestBody Customer customer) {

    return this.accountsRepository.findByCustomerId(customer.getId());
  }

}
