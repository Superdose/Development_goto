package com.eazybytes.springsecuritysection2.config;

import com.eazybytes.springsecuritysection2.filter.AuthoritiesLoggingAfterFilter;
import com.eazybytes.springsecuritysection2.filter.AuthoritiesLoggingAtFilter;
import com.eazybytes.springsecuritysection2.filter.JWTTokenGeneratorFilter;
import com.eazybytes.springsecuritysection2.filter.JWTTokenValidatorFilter;
import com.eazybytes.springsecuritysection2.filter.RequestValidationBeforeFilter;
import java.util.Arrays;
import java.util.Collections;
import javax.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

// @Configuration marks this class as a configuration-class for spring
// @EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
// -> Enables global security methods
// -> 'PrePostEnabled' enables Spring Security's pre post annotations (method-level security)
//    -> ie. @PreAuthorize() and @PostAuthorize()
// -> 'SecuredEnabled' enables Spring Security's Secured annotations (method-level-security)
//    -> ie. @Secured()
// -> 'Jsr250Enabled' enables JSR-250 annotations(method-level-security)
//    -> ie.
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class ProjectSecurityConfig extends WebSecurityConfigurerAdapter {
// WebSecurityConfigurerAdapter -> whenever we want to define spring security behaviour,
// we have to do it while extending the WebSecurityConfigurerAdapter and overriding methods of it
// -> most important class in spring security

  // allows configuring web based security for specific http requests
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    // defaultConfigurationWhichWillSecureAllTheRequests(http);
    //configurationToDenyAllTheRequests(http);
    //configurationToPermitAllTheRequests(http);

    customConfigurationAsPerOurRequirement(http);
  }

  // configure(AuthenticationManagerBuilder auth) needs to be commented out, if we want to provide
  // a UserDetailsService to Spring Security via Spring-Bean-Declaration
  /**
  // Allows for easily building in memory authentication,
  // LDAP authentication, JDBC based authentication
  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    // userConfigurationUsingOnlyAuth(auth);
    //userConfigurationUsingInMemoryUserDetailsManager(auth);
  }
  */

  // We create a bean of type PasswordEncoder
  // Spring security will find the bean of type PasswordEncoder and will use it as the default
  // password encoder -> we dont have to specify the password encoder this way for example
  // when configuring the UserConfiguration
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  // this Spring-Bean-Declaration needs to be commented out, because we provide our own
  // implemented UserDetailsService via
  // @Service (EazyBankUserDetailsService) Spring-Bean-Declaration
  /**
  // We create a bean of type UserDetailsService
  // Spring security will find the bean of type UserDetailsService and will use it
  // This way, when a request is made and needs authentication, the loadUsersByUsername-method
  // will be called from our now provided JdbcUserDetailsManager
  // We also give the JdbcUserDetailsManager the dataSource, so it will correctly address the
  // DB provided by us
  @Bean
  public UserDetailsService userDetailsService(DataSource dataSource) {
    return new JdbcUserDetailsManager(dataSource);
  }
  */

  private void userConfigurationUsingOnlyAuth(AuthenticationManagerBuilder auth) throws Exception {
    // in memory authentication
    auth.inMemoryAuthentication()

        // define a user with a corresponding password and assign an authority
        .withUser("admin").password("12345").authorities("admin")

        // define a user with a corresponding password and assign an authority
        .and().withUser("user").password("12345").authorities("read")

        // define the password encoder to use for password "storage" and validation
        .and().passwordEncoder(NoOpPasswordEncoder.getInstance());
  }

  private void userConfigurationUsingInMemoryUserDetailsManager(
      AuthenticationManagerBuilder auth
  ) throws Exception {

    // in-memory-manager used for created users
    InMemoryUserDetailsManager userDetailsService = new InMemoryUserDetailsManager();

    // create 2 users with username/password/authority
    UserDetails user1 = User.withUsername("admin").password("12345").authorities("admin").build();
    UserDetails user2 = User.withUsername("user").password("12345").authorities("read").build();

    // add the users to the in-memory-manager
    userDetailsService.createUser(user1);
    userDetailsService.createUser(user2);

    // add the in-memory-manager to the AuthenticationManagerBuilder
    auth.userDetailsService(userDetailsService);
  }

  private void defaultConfigurationWhichWillSecureAllTheRequests(
      HttpSecurity http
  ) throws Exception {
   http.authorizeRequests()

       // any request needs to be authenticated
       .anyRequest().authenticated()

       // apply the given request-constraints also to formLogin requests ("requests from Browser")
       // and httpBasic requests ("requests from a non-UI client like postman")
       .and().formLogin()
       .and().httpBasic();
  }

  private void customConfigurationAsPerOurRequirement(HttpSecurity http) throws Exception {

    // Disabling standard JSESSION authentication (and authorization)
    // -> We use JWT instead
    this.disableStandardSessionPolicy(http);

    this.configureCorsPolicy(http);

    // We disabled csrf here, because with JWT we are implementing a token, which
    // is used to check whether the accessing user is allowed to
    this.disableCsrf(http);
    /*this.configureCsrfPolicy(http);*/

    this.configureFilter(http);
    
    http.authorizeRequests()

        // Requests matching "/myAccount", "/myBalance" etc. need to be authenticated
        // hasAuthority(String) -> user needs this specific authority
        /*.antMatchers("/myAccount").hasAuthority("UPDATE")
        .antMatchers("/myBalance").hasAuthority("READ")
        .antMatchers("/myLoans").hasAuthority("DELETE")*/

        // Requests matching "/myAccount", "/myBalance" etc. need to be authenticated
        // hasRole(String) -> user needs this specific Role
        // (Roles stored in DB need the prefix "ROLE_", so Spring recognizes them as Roles)
        .antMatchers("/myAccount").hasRole("USER")
        .antMatchers("/myBalance").hasAnyRole("USER", "ADMIN")
        .antMatchers("/myCards").hasAnyRole("USER", "ADMIN")

        // authenticated() -> user just needs to be authenticated in general
        .antMatchers("/myLoans").authenticated()
        .antMatchers("/user").authenticated()

        // requests matching "/notices" and "/contact" dont need authentication
        .antMatchers("/notices").permitAll()
        .antMatchers("/contact").permitAll()

        // apply the given request-constraints also to formLogin requests ("requests from Browser")
        // and httpBasic requests ("requests from a non-UI client like postman")
        .and().formLogin()
        .and().httpBasic();
  }

  private void disableStandardSessionPolicy(HttpSecurity http) throws Exception {

    // Dont (let spring) create any (standard) HTTP-Sessions and Tokens
    http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and();
  }

  private void configureFilter(HttpSecurity http) {

    // adds our manually created RequestValidationBeforeFilter and AuthoritiesLoggingAfterFilter
    // before and after the BasicAuthenticationFilter in the spring security filterchain
    http.addFilterBefore(new RequestValidationBeforeFilter(), BasicAuthenticationFilter.class)
        .addFilterAfter(new AuthoritiesLoggingAfterFilter(), BasicAuthenticationFilter.class);

    // adds our manually created AuthoritiesLoggingAtFilter at the same position as
    // the BasicAuthenticationFilter in the spring security filterchain
    // -> the order of execution for both filters is not reproducible (i.e. not guaranteed)
    http.addFilterAt(new AuthoritiesLoggingAtFilter(), BasicAuthenticationFilter.class);

    // adds the JWTTokenValidatorFilter before the BasicAuthenticationFilter
    // in the spring security filterchain -> needed for JWT
    http.addFilterBefore(new JWTTokenValidatorFilter(), BasicAuthenticationFilter.class);

    // adds the JWTTokenGeneratorFilter after the BasicAuthenticationFilter
    // in the spring security filterchain -> needed for JWT
    http.addFilterAfter(new JWTTokenGeneratorFilter(), BasicAuthenticationFilter.class);
  }

  private void configureCsrfPolicy(HttpSecurity http) throws Exception{

    http.csrf()

        // Ignore CSRF configuration for path '/contact'
        .ignoringAntMatchers("/contact")

        // Generates a CSRF-Token everytime a fresh request comes in
        // Persists the CSRF token in a cookie named "XSRF-TOKEN" and reads
        // from the header "X-XSRF-TOKEN" (following the conventions of AngularJS).
        // When using with AngularJS be sure to use withHttpOnlyFalse().
        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
        .and();

    // this.disableCsrf(http);
  }

  private void disableCsrf(HttpSecurity http) throws Exception{

    // Disables csrf generally
    http.csrf().disable();
  }

  private void configureCorsPolicy(HttpSecurity http) throws Exception {

    // Configure cors-details on the backend application
    http.cors().configurationSource(new CorsConfigurationSource() {
      @Override
      public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
        CorsConfiguration config = new CorsConfiguration();

        // Only allow localhost:4200 as origin for cors
        config.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));

        // Allow all methods for cors
        config.setAllowedMethods(Collections.singletonList("*"));

        // Expose the responses from the corresponding requests to the frontend javascript
        config.setAllowCredentials(true);

        // Allow all (default) headers for cors (configuration for default headers)
        config.setAllowedHeaders(Collections.singletonList("*"));

        // Expose the "Authorization" header in cors (configuration for non default headers)
        // Here this is needed for JWT
        config.setExposedHeaders(Arrays.asList("Authorization"));

        // Clients can cache the response from a pre-flight request to a max of 3600 seconds
        config.setMaxAge(3600L);

        return config;
      }
    }).and(); // and() because we want to configure further outside this method

  }

  private void configurationToDenyAllTheRequests(HttpSecurity http) throws Exception {
    http.authorizeRequests()

        // any request will be denied
        .anyRequest().denyAll()

        // apply the given request-constraints also to formLogin requests ("requests from Browser")
        // and httpBasic requests ("requests from a non-UI client like postman")
        .and().formLogin()
        .and().httpBasic();
  }

  private void configurationToPermitAllTheRequests(HttpSecurity http) throws Exception {
    http.authorizeRequests()

        // permit all requests (no security)
        .anyRequest().permitAll()

        // apply the given request-constraints also to formLogin requests ("requests from Browser")
        // and httpBasic requests ("requests from a non-UI client like postman")
        .and().formLogin()
        .and().httpBasic();
  }

}
