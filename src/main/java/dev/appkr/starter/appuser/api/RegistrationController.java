package dev.appkr.starter.appuser.api;

import dev.appkr.starter.appuser.service.dto.RegistrationRequest;
import dev.appkr.starter.appuser.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/registration")
@RequiredArgsConstructor
public class RegistrationController {

  private final RegistrationService service;

  @PostMapping
  public ResponseEntity<String> register(@RequestBody RegistrationRequest req) {
    return ResponseEntity.ok(service.register(req));
  }

  @GetMapping(path = "confirm")
  public ResponseEntity<String> confirm(@RequestParam("token") String token) {
    service.confirm(token);
    return ResponseEntity.ok("이메일 계정이 확인되었습니다. 이제 로그인할 수 있습니다");
  }
}