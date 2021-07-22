package dev.appkr.starter.appuser.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RegistrationRequest {

  private final String username;
  private final String password;
  private final String email;
}
