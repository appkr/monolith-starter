package dev.appkr.starter.appuser.service;

import dev.appkr.starter.appuser.domain.AppUser;
import dev.appkr.starter.appuser.domain.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AppUserService implements UserDetailsService {

  private final AppUserRepository repository;

  @Override
  @Transactional(readOnly = true)
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    final Optional<AppUser> appUserOptional = repository.findByUsername(username);
    return appUserOptional.orElseThrow(() -> new UsernameNotFoundException("Bad credential"));
  }
}
