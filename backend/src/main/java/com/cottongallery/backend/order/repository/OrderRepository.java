package com.cottongallery.backend.order.repository;

import com.cottongallery.backend.auth.domain.Account;
import com.cottongallery.backend.order.domain.Order;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    Slice<Order> findByAccount(Account account, Pageable pageable);
    Optional<Order> findByAccountAndId(Account account, Long orderId);
}
