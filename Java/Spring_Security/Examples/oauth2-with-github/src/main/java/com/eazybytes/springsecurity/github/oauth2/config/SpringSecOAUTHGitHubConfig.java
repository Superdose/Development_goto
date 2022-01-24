package com.eazybytes.springsecurity.github.oauth2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;

public class SpringSecOAUTHGitHubConfig extends WebSecurityConfigurerAdapter {

  @Override
  protected void configure(HttpSecurity http) throws Exception {

    // any Request needs to be authenticated
    // and set the login type to the oauth2 login
    http.authorizeRequests().anyRequest().authenticated().and().oauth2Login();
  }

  // All this configuration is applicable. But we want to use oauth2 with Github
  // Spring Security OAuth2 already provides alot of configuration via .properties
  // to enable oauth2 with external oauth2 providers
  // -> supported external providers in .properties -> Google, Facebook, Github, Okta
  // -> because we want to use the external oauth2 provider by github, we make use of .properties
  // here -> still, the manual configuration in this class is also viable
  // -> you have to configure via class, if you want to use your own oauth2 provider or a provider,
  // which spring security doesnt support by itself
  /*
  private ClientRegistration clientRegistration() {

    // Registering the client details for OAuth2 (GitHub)
    // 'What oauth server are we using'
    return CommonOAuth2Provider.GITHUB.getBuilder("github")
        .clientId("a14bdf646004f0563c3f")
        .clientSecret("6578b35ce7e41ef9d8883a017599c28ee652eb0f")
        .build();
  }*/

  // The same as the client details registration with the CommonOAuth2Provider
  // In this case we configure everything manually
  // -> useful when configuring an oauth2 server in your own organisation
  /*private ClientRegistration clientRegistration() {
    ClientRegistration cr =
        ClientRegistration
        .withRegistrationId("github")
        .clientId("a14bdf646004f0563c3f")
        .clientSecret("6578b35ce7e41ef9d8883a017599c28ee652eb0f")
        .scope(new String[] {"read:user"})
        .authorizationUri("https://github.com/login/oauth/authorize")
        .tokenUri("https://github.com/login/oauth/access_token")
        .userInfoUri("https://api.github.com/user")
        .userNameAttributeName("id").clientName("GitHub")
        .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
        .redirectUriTemplate("{baseUrl}/{action}/oauth2/code/{registrationId}")
        .build();

        return cr;
  } */

  /*@Bean
  public ClientRegistrationRepository clientRegistrationRepository() {

    // Using an in-memory client registration repository here
    // -> 'same' as UserDetailsService -> will use the provided registrationId to get the
    // client registration details, that we configured
    ClientRegistration clientRegistration = this.clientRegistration();
    return new InMemoryClientRegistrationRepository(clientRegistration);
  }*/
}
