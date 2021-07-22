package dev.appkr.starter.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {

  private String version = "0.0.1.SNAPSHOT";
  private String secret = "VUjx6MNon86YtxPxAAoJtIKbwSIkBGan0KriPgsb3E61c4zd";
  private Integer jwtValiditySeconds = 60 * 60 * 24; // 1 day
}