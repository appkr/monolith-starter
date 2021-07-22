package dev.appkr.starter.api;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeResource {

  @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
  @GetMapping(path = "/")
  public String home() {
    return "Hello visitor";
  }

  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping(path = "/users")
  public String user(Authentication authentication) {
    return "Hello " + authentication.getName();
  }

  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping(path = "/admin")
  public String admin(Authentication authentication) {
    return "Hello " + authentication.getName();
  }
}
