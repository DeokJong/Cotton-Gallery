package com.cottongallery.backend.auth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cottongallery.backend.auth.domain.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {
  public Optional<Account> findByUsername(String username);

  Boolean existsByUsername(String username);
}
