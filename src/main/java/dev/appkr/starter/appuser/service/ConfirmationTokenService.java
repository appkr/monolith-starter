package dev.appkr.starter.appuser.service;

import dev.appkr.starter.appuser.domain.AppUser;
import dev.appkr.starter.appuser.domain.ConfirmationToken;
import dev.appkr.starter.appuser.domain.ConfirmationTokenRepository;
import dev.appkr.starter.config.ApplicationProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.Instant;

@Service
@RequiredArgsConstructor
public class ConfirmationTokenService {

  private final ConfirmationTokenRepository repository;
  private final ApplicationProperties applicationProperties;

  @Transactional
  public ConfirmationToken createFor(AppUser appUser) {
    final Integer validMinutes = applicationProperties.getConfirmationTokenValidityMinutes();
    final ConfirmationToken confirmationToken = ConfirmationToken.createFor(appUser, validMinutes);

    return repository.save(confirmationToken);
  }

  @Transactional(readOnly = true)
  public ConfirmationToken findNotExpiredBy(String token) {
    return repository.findByToken(token, Instant.now()).orElseThrow(() -> new EntityNotFoundException());
  }
}
