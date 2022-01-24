package com.eazybytes.springsecuritysection2.controller;

import com.eazybytes.springsecuritysection2.entity.Customer;
import com.eazybytes.springsecuritysection2.repository.CustomerRepository;
import java.security.Principal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LoginController {

  private final CustomerRepository customerRepository;

  @GetMapping("/user")
  public Customer getUserDetailsAfterLogin(Principal user) {
    List<Customer> customers = this.customerRepository.findByEmail(user.getName());

    if(customers.isEmpty()) return null;
    else return customers.get(0);
  }
}
