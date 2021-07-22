package dev.appkr.starter.appuser.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.RandomStringUtils;

import javax.persistence.*;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Entity
@Table(name = "confirmation_tokens")
@Data
@EqualsAndHashCode(of = {"id"})
public class ConfirmationToken {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String token;
  private Instant createdAt;
  private Instant expiresAt;
  private Instant confirmedAt;
  @ManyToOne
  @JoinColumn(name = "user_id")
  private AppUser appUser;

  public ConfirmationToken(String token, Instant createdAt, Instant expiresAt, AppUser appUser) {
    this.token = token;
    this.createdAt = createdAt;
    this.expiresAt = expiresAt;
    this.appUser = appUser;
  }

  protected ConfirmationToken() {}

  public static ConfirmationToken createFor(AppUser appUser, int validMinutes) {
    return new ConfirmationToken(
        RandomStringUtils.randomAlphanumeric(32),
        Instant.now(),
        Instant.now().plus(validMinutes, ChronoUnit.MINUTES),
        appUser
    );
  }

  public void markConfirmed() {
    this.confirmedAt = Instant.now();
  }
}