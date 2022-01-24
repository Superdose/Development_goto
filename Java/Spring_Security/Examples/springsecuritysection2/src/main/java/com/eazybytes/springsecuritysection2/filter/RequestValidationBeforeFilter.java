package com.eazybytes.springsecuritysection2.filter;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.util.StringUtils;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

// Filter that is to be used in Spring Security
// Added in our ProjectSecurityConfiguration (extends WebSecurityConfigurerAdapter)
// Implements javax.servlet.Filter
public class RequestValidationBeforeFilter implements Filter {

  public static final String AUTHENTICATION_SCHEME_BASIC = "Basic";

  private Charset credentialCharset = StandardCharsets.UTF_8;

  // Method that has to be overwritten in a Filter
  // This is a sample implementation, which checks whether the
  // authorization header contains 'test'
  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
      FilterChain filterChain) throws IOException, ServletException {

    HttpServletRequest req = (HttpServletRequest) servletRequest;
    HttpServletResponse res = (HttpServletResponse) servletResponse;

    String header = req.getHeader(AUTHORIZATION);

    if(header != null) {
      header = header.trim();

      if(StringUtils.startsWithIgnoreCase(header, AUTHENTICATION_SCHEME_BASIC)) {
        byte[] base64Token = header.substring(6).getBytes(StandardCharsets.UTF_8);
        byte[] decoded;

        try{

          decoded = Base64.getDecoder().decode(base64Token);
          String token = new String(decoded, this.getCredentialCharset(req));

          int delim = token.indexOf(":");

          if(delim == -1) {
            throw new BadCredentialsException("Invalid basic authentication token");
          }

          String email = token.substring(0, delim);

          if(email.toLowerCase().contains("test")) {
            res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
          }

        } catch (IllegalArgumentException e) {
          throw new BadCredentialsException("Failed to decode basic authentication token");
        }
      }
    }

    // At the end of a filter we have to call doFilter(servletRequest, servletResponse) so the
    // upcoming filter in the filterchain will be executed
    filterChain.doFilter(servletRequest, servletResponse);
  }

  protected Charset getCredentialCharset(HttpServletRequest request) {
      return this.getCredentialCharset();
  }

  public Charset getCredentialCharset() {
    return this.credentialCharset;
  }

}
