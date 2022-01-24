package com.eazybytes.springsecuritysection2.config;

import com.eazybytes.springsecuritysection2.entity.Customer;
import com.eazybytes.springsecuritysection2.model.SecurityCustomer;
import com.eazybytes.springsecuritysection2.repository.CustomerRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// This class is our own UserDetailsService implementation for our own use case
@Service
@RequiredArgsConstructor
public class EazyBankUserDetailsService implements UserDetailsService {

  private final CustomerRepository customerRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    List<Customer> customerList = this.customerRepository.findByEmail(username);

    if (customerList.isEmpty())
      throw new UsernameNotFoundException("User details not found for the user: " + username);

    return new SecurityCustomer(customerList.get(0));
  }
}
