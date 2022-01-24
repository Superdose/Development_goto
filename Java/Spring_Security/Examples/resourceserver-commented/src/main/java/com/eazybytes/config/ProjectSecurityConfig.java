package com.eazybytes.config;

import java.util.Collections;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

// @Configuration marks this class as a configuration-class for spring
@Configuration
public class ProjectSecurityConfig extends WebSecurityConfigurerAdapter {
// WebSecurityConfigurerAdapter -> whenever we want to define spring security behaviour,
// we have to do it while extending the WebSecurityConfigurerAdapter and overriding methods of it
// -> most important class in spring security

	/**
	 * /myAccount - Secured /myBalance - Secured /myLoans - Secured /myCards -
	 * Secured /notices - Not Secured /contact - Not Secured
	 */
	// allows configuring web based security for specific http requests
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		// We prepare/setup a custom JWT-Authentication-Converter to convert all the roles
		// present inside the token, so we can pass them later onto the spring-security-framework
		JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
		jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(new KeycloakRoleConverter());

		// // Configure cors-details on the backend application
		// -> (for the frontend-application on http://localhost:4200)
		http.cors().configurationSource(new CorsConfigurationSource() {
			@Override public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
				CorsConfiguration config = new CorsConfiguration();

				// Only allow localhost:4200 as origin for cors
				config.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));

				// Allow all methods for cors
				config.setAllowedMethods(Collections.singletonList("*"));

				// Expose the responses from the corresponding requests to the frontend javascript
				config.setAllowCredentials(true);

				// Allow all (default) headers for cors (configuration for default headers)
				config.setAllowedHeaders(Collections.singletonList("*"));

				// Clients can cache the response from a pre-flight request to a max of 3600 seconds
				config.setMaxAge(3600L);

				return config;
			}
		}).and()
				.authorizeRequests()

				// Requests matching "/myAccount", "/myBalance" etc. need to be authenticated
				// hasRole(String) -> user needs this specific Role
				// hasAnyRole(String..) -> user needs at least one of the specified roles
				.antMatchers("/myAccount").hasAnyRole("USER")
				.antMatchers("/myBalance").hasAnyRole("ADMIN")
				.antMatchers("/myCards").hasAnyRole("USER", "ADMIN")

				// authenticated() -> user just needs to be authenticated in general
				.antMatchers("/myLoans").authenticated()

				// requests matching "/notices" and "/contact" dont need authentication
				.antMatchers("/notices").permitAll()
				.antMatchers("/contact").permitAll()

				// OAuth2 provides CSRF-Token
				.and().csrf().disable()

				// We are an OAuth2-Resource-Server, which follow JWT-Standards
				// Keycloak maintains all the role details inside JWT
				// -> we provide a custom JWT-Authentication-Converter to convert all the roles
				// present inside the token and pass them onto the spring-security-framework
				.oauth2ResourceServer().jwt().jwtAuthenticationConverter(jwtAuthenticationConverter);
		
		/**
		 * This configuration is needed to view the /h2-console with out any issues.
		 * Since H2 Console uses frames to display the UI, we need to allow the frames.
		 * Otherwise since by default Spring Security consider X-Frame-Options: DENY
		 * to avoid Clickjacking attacks, the /h2-console will not work properly.
		 * More details can be found at 
		 * https://docs.spring.io/spring-security/site/docs/5.0.x/reference/html/headers.html#headers-frame-options
		 */
		http.headers().frameOptions().sameOrigin();

	}

}
