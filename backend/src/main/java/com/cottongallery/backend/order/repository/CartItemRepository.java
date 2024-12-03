package com.cottongallery.backend.order.repository;

import com.cottongallery.backend.order.domain.CartItem;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    @EntityGraph(attributePaths = {"item", "account"})
    Optional<CartItem> findByIdAndCreatedBy(Long cartItemId, String createdBy);

    @EntityGraph(attributePaths = {"item", "account", "item.discount"})
    List<CartItem> findAllByCreatedBy(String createdBy);
}
