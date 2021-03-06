package dev.appkr.starter.api;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeResource {

  @GetMapping(path = "/")
  public String home() {
    return "Hello visitor";
  }

  @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
  @GetMapping(path = "/users")
  public String user() {
    final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    return "Hello " + authentication.getName();
  }

  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping(path = "/admin")
  public String admin() {
    final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    return "Hello " + authentication.getName();
  }
}
