package com.cottongallery.backend.auth.repository;

import com.cottongallery.backend.auth.domain.Account;
import com.cottongallery.backend.auth.domain.Address;
import com.cottongallery.backend.auth.domain.AddressType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Long> {
    List<Address> findAllByAccount(Account account);

    Optional<Address> findByIdAndCreatedBy(Long addressId, String createBy);
    Optional<Address> findByAddressTypeAndAccount(AddressType addressType, Account account);
}
