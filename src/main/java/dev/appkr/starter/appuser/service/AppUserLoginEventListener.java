package dev.appkr.starter.appuser.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AppUserLoginEventListener {

  @EventListener
  public void handle(AuthenticationSuccessEvent e) {
    final UserDetails userDetails = (UserDetails) e.getAuthentication().getPrincipal();
    log.info("A user named '{}' logged in", userDetails.getUsername());
  }
}