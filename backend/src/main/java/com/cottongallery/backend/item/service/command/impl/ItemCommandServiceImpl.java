package com.cottongallery.backend.item.service.command.impl;

import com.cottongallery.backend.item.domain.Discount;
import com.cottongallery.backend.item.domain.Item;
import com.cottongallery.backend.item.dto.request.ItemCreateRequest;
import com.cottongallery.backend.item.dto.request.ItemUpdateRequest;
import com.cottongallery.backend.item.exception.ItemNotFoundException;
import com.cottongallery.backend.item.repository.DiscountRepository;
import com.cottongallery.backend.item.repository.ItemRepository;
import com.cottongallery.backend.item.service.command.ItemCommandService;
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
                discount);

        Item savedItem = itemRepository.save(item);

        return savedItem.getId();
    }

    @Override
    public Long updateItem(ItemUpdateRequest itemUpdateRequest, Long itemId, Long discountId) {
        Discount discount = Optional.ofNullable(discountId)
                .flatMap(discountRepository::findById)
                .orElse(null);

        Item item = itemRepository.findById(itemId).orElseThrow(ItemNotFoundException::new);

        item.update(itemUpdateRequest.getName(),
                itemUpdateRequest.getPrice(),
                itemUpdateRequest.getStockQuantity(),
                itemUpdateRequest.getContent(),
                discount);

        return item.getId();
    }

    @Override
    public void deleteItem(Long itemId) {
        itemRepository.deleteById(itemId);
    }
}
