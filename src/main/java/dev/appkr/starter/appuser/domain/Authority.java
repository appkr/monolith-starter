package dev.appkr.starter.appuser.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "authorities")
@Data
@EqualsAndHashCode(of = {"id"})
@ToString(exclude = {"users"})
public class Authority implements GrantedAuthority {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String authority;
  @ManyToMany(mappedBy = "authorities")
  private Set<AppUser> users;

  public Authority(String authority) {
    this.authority = authority;
  }

  protected Authority() {
  }
}
