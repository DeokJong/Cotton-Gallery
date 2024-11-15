package com.cottongallery.backend.item.repository;

import com.cottongallery.backend.item.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
