package com.eazybytes.springsecuritysection2.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.GenericFilterBean;

// Filter that is to be used in Spring Security
// Added in our ProjectSecurityConfiguration (extends WebSecurityConfigurerAdapter)
// Implements javax.servlet.Filter
public class AuthoritiesLoggingAtFilter implements Filter {

  private Logger logger = LoggerFactory.getLogger(AuthoritiesLoggingAtFilter.class);

  // Method that has to be overwritten in a Filter
  // This is a sample implementation, which is simply logging for the sake of logging
  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
      FilterChain filterChain) throws IOException, ServletException {

    this.logger.info("Authentication Validation is in progress");

    // At the end of a filter we have to call doFilter(servletRequest, servletResponse) so the
    // upcoming filter in the filterchain will be executed
    filterChain.doFilter(servletRequest, servletResponse);
  }
}
