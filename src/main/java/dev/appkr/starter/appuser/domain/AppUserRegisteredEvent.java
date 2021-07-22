package dev.appkr.starter.appuser.domain;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class AppUserRegisteredEvent extends ApplicationEvent {

  private final String body;

  public AppUserRegisteredEvent(AppUser source, String body) {
    super(source);
    this.body = body;
  }
}