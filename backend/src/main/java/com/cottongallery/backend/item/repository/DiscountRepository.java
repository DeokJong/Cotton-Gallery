package com.cottongallery.backend.item.repository;

import com.cottongallery.backend.item.domain.Discount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiscountRepository extends JpaRepository<Discount, Long> {
}
