package com.rlj.dietAssist.jwt;

import java.util.Collection;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;

@Getter
@AllArgsConstructor
public class CustomUserDetails implements UserDetails {

  private final Long id;
  private final String email;
  private final String password;
  private final String name;
  private final Collection<? extends GrantedAuthority> authorities;

  @Override
  public String getUsername() {
    return "";
  }
}
