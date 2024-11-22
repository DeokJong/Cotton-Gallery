package com.cottongallery.backend.auth.repository;

import com.cottongallery.backend.auth.domain.Account;
import com.cottongallery.backend.order.domain.Address;
import com.cottongallery.backend.order.domain.AddressType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Long> {
    Optional<Address> findByAccountAndAddressType(Account account, AddressType addressType);
}
