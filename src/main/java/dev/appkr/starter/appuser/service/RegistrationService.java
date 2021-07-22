package dev.appkr.starter.appuser.service;

import dev.appkr.starter.appuser.domain.*;
import dev.appkr.starter.appuser.service.dto.RegistrationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RegistrationService {

  private final EmailValidator emailValidator;
  private final AppUserRepository repository;
  private final AuthorityRepository authorityRepository;
  private final PasswordEncoder passwordEncoder;
  private final ConfirmationTokenService confirmationTokenService;
  private final ApplicationEventPublisher eventPublisher;

  @Transactional
  public String register(RegistrationRequest req) {
    if (!emailValidator.test(req.getEmail())) {
      throw new IllegalArgumentException("Invalid email");
    }

    final Optional<AppUser> optionalAppUser = repository.findByUsername(req.getUsername());
    if (optionalAppUser.isPresent()) {
      throw new IllegalArgumentException("The username is taken");
    }

    AppUser appUser = new AppUser(req.getUsername(), passwordEncoder.encode(req.getPassword()), req.getEmail());
    Authority authority = Authority.get(authorityRepository, Authority.ROLE_USER);
    appUser.addAuthority(authority);
    repository.save(appUser);

    final ConfirmationToken confirmationToken = confirmationTokenService.createFor(appUser);

    final String link = ServletUriComponentsBuilder.fromCurrentRequest()
        .path("/confirm")
        .queryParam("token", confirmationToken.getToken())
        .toUriString();
    eventPublisher.publishEvent(new AppUserRegisteredEvent(appUser,
        String.format("%s 링크를 방문하여 사용자 등록을 확인해주세요", link)));

    return confirmationToken.getToken();
  }

  @Transactional
  public void confirm(String token) {
    ConfirmationToken confirmationToken = confirmationTokenService.findNotExpiredBy(token);
    confirmationToken.markConfirmed();
    confirmationToken.getAppUser().activate();
  }
}
