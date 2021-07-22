package dev.appkr.starter.appuser.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
@EqualsAndHashCode(of = {"id"})
public class AppUser implements UserDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String username;
  private String password;
  private String email;
  private boolean enabled;
  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "authority_user",
      joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
      inverseJoinColumns = @JoinColumn(name = "authority_id", referencedColumnName = "id")
  )
  private Set<Authority> authorities = new HashSet<>();
  private Instant createdAt;
  private Instant updatedAt;

  public AppUser(String username, String password, String email, boolean enabled) {
    this.username = username;
    this.password = password;
    this.email = email;
    this.enabled = enabled;
  }

  protected AppUser() {
  }

  @Override
  public boolean isAccountNonExpired() {
    return enabled;
  }

  @Override
  public boolean isAccountNonLocked() {
    return enabled;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return enabled;
  }
}
