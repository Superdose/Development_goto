package com.eazybytes.springsecuritysection2.model;

import com.eazybytes.springsecuritysection2.entity.Customer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

// This class is our own UserDetails implementation for our own use case
@RequiredArgsConstructor
public class SecurityCustomer implements UserDetails {

  private static final long serialVersionUID = -6669763513257064103L;

  private final Customer customer;

  // returns the list of authorities of this user
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    List<GrantedAuthority> authorities = new ArrayList();
    authorities.add(new SimpleGrantedAuthority(this.customer.getRole()));

    return authorities;
  }

  @Override
  public String getPassword() {
    return this.customer.getPwd();
  }

  @Override
  public String getUsername() {
    return this.customer.getEmail();
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
