package com.eazybytes.springsecuritysection2.controller;

import com.eazybytes.springsecuritysection2.entity.Customer;
import com.eazybytes.springsecuritysection2.entity.Loans;
import com.eazybytes.springsecuritysection2.repository.LoanRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LoansController {

  private final LoanRepository loanRepository;

  // This is not best practice concerning api design -> the course used it like this
  @PostMapping("/myLoans")
  public List<Loans> getLoanDetails(@RequestBody Customer customer) {
    return this.loanRepository.findByCustomerIdOrderByStartDtDesc(customer.getId());
  }

}
