package com.eazybytes.springsecuritysection2.config;

import com.eazybytes.springsecuritysection2.entity.Authority;
import com.eazybytes.springsecuritysection2.entity.Customer;
import com.eazybytes.springsecuritysection2.repository.CustomerRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

// Sample AuthenticationProvider implemented by our own,
// so we can fully customize the process of authentication
// This serves as an example
// @Component -> Spring will register the bean and use it as an AuthenticationProvider
@Component
@RequiredArgsConstructor
public class EazyBankUsernamePwdAuthenticationProvider implements AuthenticationProvider {

  private final CustomerRepository customerRepository;

  private final PasswordEncoder passwordEncoder;

  // Method used for the actual authentication (by the above AuthenticationManager)
  @Override
  public Authentication authenticate(
      Authentication authentication
  ) throws AuthenticationException {

    // We get the username and password of the user
    String username = authentication.getName();
    String pwd = authentication.getCredentials().toString();

    // we get the user (in our own DB) with the username
    List<Customer> customerList = this.customerRepository.findByEmail(username);

    if (!customerList.isEmpty()) {

      // use the PasswordEncoder to match the given and stored password
      if (this.passwordEncoder.matches(pwd, customerList.get(0).getPwd())) {

        // save the corresponding authorities of the user
        /* List<GrantedAuthority> authorities = new ArrayList();
        authorities.add(new SimpleGrantedAuthority(customerList.get(0).getRole())); */

        // return the UsernamePasswordAuthenticationToken, which in
        // itself implements the Authentication interface (therefore returnValue)
        return new UsernamePasswordAuthenticationToken(
            username,
            pwd,
            this.getGrantedAuthorities(customerList.get(0).getAuthorities())
        );
      } else {
        throw new BadCredentialsException("Invalid password!");
      }
    } else {
      throw new BadCredentialsException("No user registered with this details!");
    }

  }

  private List<GrantedAuthority> getGrantedAuthorities(Set<Authority> authorities) {

    List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

    // save the corresponding authorities of the user
    for(Authority authority : authorities) {
      grantedAuthorities.add(new SimpleGrantedAuthority(authority.getName()));
    }

    return grantedAuthorities;
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return authentication.equals(UsernamePasswordAuthenticationToken.class);
  }
}
