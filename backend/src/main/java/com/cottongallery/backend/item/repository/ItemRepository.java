package com.cottongallery.backend.item.repository;

import com.cottongallery.backend.item.domain.Item;
import com.cottongallery.backend.item.domain.ItemStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {

    Slice<Item> findAllByItemStatus(Pageable pageable, ItemStatus itemStatus);
    Slice<Item> findAllByItemStatusAndNameContaining(Pageable pageable, ItemStatus itemStatus, String keyword);
}
