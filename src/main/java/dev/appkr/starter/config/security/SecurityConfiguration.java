package dev.appkr.starter.config.security;

import dev.appkr.starter.appuser.JWTRequestFilter;
import dev.appkr.starter.appuser.domain.AppUser;
import dev.appkr.starter.appuser.domain.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.boot.actuate.health.HealthEndpoint;
import org.springframework.boot.actuate.info.InfoEndpoint;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.transaction.annotation.Transactional;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

  private final UserDetailsService userDetailsService;
  private final JWTRequestFilter jwtRequestFilter;
  private final AppUserRepository userRepository;

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsService);
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        .cors()
        .and()
        .csrf().disable()
        .headers().frameOptions().disable();

    http
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    http
        .authorizeRequests()
        .antMatchers("/", "/profile", "/login/**", "/oauth2/**", "/authenticate/**", "/registration/**", "/js/**", "/css/**", "/error").permitAll()
        .requestMatchers(EndpointRequest.to(HealthEndpoint.class, InfoEndpoint.class)).permitAll()
        .anyRequest().authenticated();

    http
        .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

    http.oauth2Login();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Bean
  public OAuth2UserService oAuth2UserService() {
    return new DefaultOAuth2UserService() {
      @Override
      public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        final String registrationId = userRequest.getClientRegistration().getRegistrationId();
        final OAuth2User oAuth2User = super.loadUser(userRequest);

        handleOAuth2User(registrationId, oAuth2User);

        return oAuth2User;
      }
    };
  }

  @Transactional
  public void handleOAuth2User(String registrationId, OAuth2User oAuth2User) {
    String email = oAuth2User.getAttribute("email");

    try {
      // TODO: update existing ApplicationUser if the email DOES exist
      final UserDetails userDetails = userDetailsService.loadUserByUsername(email);
      if (userDetails.getUsername().equals(oAuth2User.getAttribute("name"))) {
        return;
      }
    } catch (UsernameNotFoundException e) {
      AppUser appUser = new AppUser(oAuth2User.getAttribute("name"), passwordEncoder().encode("password"), email);
      appUser.activate();
      userRepository.save(appUser);
    }
  }
}
