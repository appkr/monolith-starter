package dev.appkr.starter.appuser.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.Optional;

public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, Long> {

  @Query("SELECT t FROM ConfirmationToken t WHERE t.token = :token AND t.expiresAt >= :now AND t.confirmedAt IS NULL")
  Optional<ConfirmationToken> findByToken(@Param("token") String token, @Param("now") Instant now);
}
