package dev.appkr.starter.appuser.service.dto;

import lombok.Getter;

@Getter
public class AccessToken {

  private final String accessToken;
  private final String tokenType = "bearer";
  private final Long iat;
  private final Long expiresIn;
  //  private final String scope = "openid";
  private final String jti;

  public AccessToken(String accessToken, Long iat, Long expiresIn, String jti) {
    this.accessToken = accessToken;
    this.iat = iat;
    this.expiresIn = expiresIn;
    this.jti = jti;
  }
}