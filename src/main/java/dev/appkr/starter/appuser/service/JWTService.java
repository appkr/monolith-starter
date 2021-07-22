package dev.appkr.starter.appuser.service;

import dev.appkr.starter.appuser.service.dto.AccessToken;
import dev.appkr.starter.config.ApplicationProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JWTService {

  private final ApplicationProperties applicationProperties;

  public AccessToken generateToken(UserDetails userDetails) {
    Map<String, Object> claims = new HashMap<>();
    claims.put("authorities",
        userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));

    final Date iat = new Date(System.currentTimeMillis());
    final Date exp = new Date(System.currentTimeMillis() + (1000 * applicationProperties.getJwtValiditySeconds()));
    final String jwt = Jwts.builder()
        .setClaims(claims)
        .setSubject(userDetails.getUsername())
        .setIssuedAt(iat)
        .setExpiration(exp)
        .signWith(Keys.hmacShaKeyFor(applicationProperties.getSecret().getBytes()), SignatureAlgorithm.HS256)
        .compact();

    return new AccessToken(
        jwt, iat.getTime(), (exp.getTime() - iat.getTime()) / 1000, UUID.randomUUID().toString());
  }

  public boolean isValidToken(String token, UserDetails userDetails) {
    final String username = extractUsername(token);
    return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
  }

  private boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }

  public String extractUsername(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  public Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = Jwts.parserBuilder()
        .setSigningKey(Keys.hmacShaKeyFor(applicationProperties.getSecret().getBytes()))
        .build()
        .parseClaimsJws(token)
        .getBody();

    return claimsResolver.apply(claims);
  }
}