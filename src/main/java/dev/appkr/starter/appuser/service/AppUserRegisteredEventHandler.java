package dev.appkr.starter.appuser.service;

import dev.appkr.starter.appuser.domain.AppUser;
import dev.appkr.starter.appuser.domain.AppUserRegisteredEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class AppUserRegisteredEventHandler {

  private final EmailService emailService;

  @EventListener
  public void handle(AppUserRegisteredEvent e) {
    final AppUser appUser = (AppUser) e.getSource();
    final String body = e.getBody();

    if (true) {
      log.info("Sending an email to {} with a body of {}", appUser.getEmail(), body);
      return;
    }

    emailService.send(appUser.getEmail(), body);
  }
}