package com.eazybytes.springsecuritysection2.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

// Filter that is to be used in Spring Security
// Added in our ProjectSecurityConfiguration (extends WebSecurityConfigurerAdapter)
// Implements javax.servlet.Filter
public class AuthoritiesLoggingAfterFilter implements Filter {

  private final Logger logger = LoggerFactory.getLogger(AuthoritiesLoggingAfterFilter.class);

  // Method that has to be overwritten in a Filter
  // This is a sample implementation, which is logging authentication details
  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
      FilterChain filterChain) throws IOException, ServletException {

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if(authentication != null) {
      this.logger.info(
          "User {} is successfully authenticated and has the authorities {}",
          authentication.getName(),
          authentication.getAuthorities().toString()
      );
    }

    // At the end of a filter we have to call doFilter(servletRequest, servletResponse) so the
    // upcoming filter in the filterchain will be executed
    filterChain.doFilter(servletRequest, servletResponse);
  }
}
