package dev.appkr.starter.appuser.api;

import dev.appkr.starter.appuser.service.JWTService;
import dev.appkr.starter.appuser.service.dto.AccessToken;
import dev.appkr.starter.appuser.service.dto.AuthenticationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

  private final AuthenticationManager manager;
  private final UserDetailsService service;
  private final JWTService jwtService;

  @PostMapping(path = "/authenticate")
  public ResponseEntity<AccessToken> authenticate(@RequestBody AuthenticationRequest req) {
    try {
      manager.authenticate(new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword()));
    } catch (BadCredentialsException e) {
      throw new RuntimeException("Incorrect username or password", e);
    }

    final UserDetails userDetails = service.loadUserByUsername(req.getUsername());
    final AccessToken accessToken = jwtService.generateToken(userDetails);

    return ResponseEntity.ok(accessToken);
  }
}
