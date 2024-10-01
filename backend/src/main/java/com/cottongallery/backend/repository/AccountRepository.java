package com.cottongallery.backend.repository;

import com.cottongallery.backend.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
