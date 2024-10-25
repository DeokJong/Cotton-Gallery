package com.cottongallery.backend.auth.utils;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.cottongallery.backend.auth.domain.Account;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AccountDetails implements UserDetails {
  private final Account account;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority(account.getRole().name()));
  }

  @Override
  public String getPassword() {
    return account.getPassword();
  }

  @Override
  public String getUsername() {
    return account.getUsername();
  }

}
