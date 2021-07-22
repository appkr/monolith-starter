package dev.appkr.starter.appuser.domain;

import com.sun.nio.sctp.IllegalReceiveException;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "authorities")
@Data
@EqualsAndHashCode(of = {"authority"})
@ToString(exclude = {"users"})
public class Authority implements GrantedAuthority {

  public static final String ROLE_USER = "ROLE_USER";
  public static final String ROLE_ADMIN = "ROLE_ADMIN";

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String authority;
  @ManyToMany(mappedBy = "authorities")
  private Set<AppUser> users = new HashSet<>();

  public Authority(String authority) {
    this.authority = authority;
  }

  protected Authority() {
  }

  public static Authority get(AuthorityRepository repository, String authority) {
    return repository.findByAuthority(authority).orElseThrow(() -> new IllegalReceiveException());
  }
}
