package com.cottongallery.backend.repository;

import com.cottongallery.backend.domain.Account;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
  public Optional<Account> findByUsername(String username);

  Boolean existsByUsername(String username);
}
