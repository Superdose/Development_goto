package com.eazybytes.springsecuritysection2.filter;

import com.eazybytes.springsecuritysection2.constants.SecurityConstants;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

// Filter class for generating JWT-Tokens
// This filter is only executed once -> OncePerRequestFilter -> override doFilterInteral
public class JWTTokenGeneratorFilter extends OncePerRequestFilter {

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if(authentication != null) {
      SecretKey key =
          Keys.hmacShaKeyFor(SecurityConstants.JWT_KEY.getBytes(StandardCharsets.UTF_8));

      String jwt =
          Jwts.builder().setIssuer("Eazy Bank").setSubject("JWT Token")
              .claim("username", authentication.getName())
              .claim("authorities", populateAuthorities(authentication.getAuthorities()))
              .setIssuedAt(new Date())
              .setExpiration(new Date((new Date()).getTime() + 30000000))
              .signWith(key).compact();

      response.setHeader(SecurityConstants.JWT_HEADER, jwt);
    }

    filterChain.doFilter(request, response);
  }

  @Override
  protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {

    // Only generate the jwt, if the path is '/user' -> login path
    // ie. dont filter, if the path is not '/user'
    return !request.getServletPath().equals("/user");
  }

  private String populateAuthorities(Collection<? extends GrantedAuthority> collection) {

    Set<String> authoritiesSet = new HashSet<>();

    for (GrantedAuthority authority : collection) {
      authoritiesSet.add(authority.getAuthority());
    }

    return String.join(",", authoritiesSet);
  }
}
