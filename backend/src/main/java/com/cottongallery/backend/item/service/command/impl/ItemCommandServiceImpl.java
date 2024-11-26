package com.cottongallery.backend.item.service.command.impl;

import com.cottongallery.backend.item.domain.Discount;
import com.cottongallery.backend.item.domain.Item;
import com.cottongallery.backend.item.domain.ItemStatus;
import com.cottongallery.backend.item.dto.request.ItemCreateRequest;
import com.cottongallery.backend.item.dto.request.ItemUpdateRequest;
import com.cottongallery.backend.item.repository.DiscountRepository;
import com.cottongallery.backend.item.repository.ItemRepository;
import com.cottongallery.backend.item.service.command.ItemCommandService;
import com.cottongallery.backend.item.service.query.ItemQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ItemCommandServiceImpl implements ItemCommandService {

    private final ItemQueryService itemQueryService;

    private final DiscountRepository discountRepository;
    private final ItemRepository itemRepository;

    @Override
    public Long createItem(ItemCreateRequest itemCreateRequest, Long discountId) {
        Discount discount = Optional.ofNullable(discountId)
                .flatMap(discountRepository::findById)
                .orElse(null);

        Item item = Item.createItem(itemCreateRequest.getName(),
                itemCreateRequest.getPrice(),
                itemCreateRequest.getStockQuantity(),
                itemCreateRequest.getContent(),
                ItemStatus.ACTIVE,
                discount);

        Item savedItem = itemRepository.save(item);

        return savedItem.getId();
    }

    @Override
    public Long updateItem(ItemUpdateRequest itemUpdateRequest, Long itemId, Long discountId) {
        Discount discount = Optional.ofNullable(discountId)
                .flatMap(discountRepository::findById)
                .orElse(null);

        Item item = itemQueryService.getItemEntityById(itemId);

        item.update(itemUpdateRequest.getName(),
                itemUpdateRequest.getPrice(),
                itemUpdateRequest.getStockQuantity(),
                itemUpdateRequest.getContent(),
                discount);

        return item.getId();
    }

    @Override
    public void deleteItem(Long itemId) {
        Item item = itemQueryService.getItemEntityById(itemId);

        item.changeItemStatus(ItemStatus.DELETED);
    }
}
