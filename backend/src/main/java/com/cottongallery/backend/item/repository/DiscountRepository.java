package com.cottongallery.backend.item.repository;

import com.cottongallery.backend.item.domain.Discount;
import com.cottongallery.backend.item.constants.DiscountStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;


public interface DiscountRepository extends JpaRepository<Discount, Long> {
    Slice<Discount> findAllByDiscountStatus(Pageable pageable, DiscountStatus discountStatus);
}
