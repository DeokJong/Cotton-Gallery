package com.cottongallery.backend.item.service.query.impl;

import com.cottongallery.backend.item.domain.Item;
import com.cottongallery.backend.item.domain.ItemStatus;
import com.cottongallery.backend.item.dto.response.ItemListResponse;
import com.cottongallery.backend.item.exception.ItemNotFoundException;
import com.cottongallery.backend.item.repository.ItemRepository;
import com.cottongallery.backend.item.service.query.ItemQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemQueryServiceImpl implements ItemQueryService {

    private final ItemRepository itemRepository;

    @Override
    public Slice<ItemListResponse> getItemResponses(Pageable pageable) {
        return itemRepository
                .findAllByItemStatus(pageable, ItemStatus.ACTIVE)
                .map(ItemListResponse::fromItem);
    }

    @Override
    public Item getItemEntityById(Long itemId) {
        return itemRepository
                .findById(itemId)
                .orElseThrow(ItemNotFoundException::new);
    }

}
