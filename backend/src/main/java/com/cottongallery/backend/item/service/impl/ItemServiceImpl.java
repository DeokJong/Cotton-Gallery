package com.cottongallery.backend.item.service.impl;

import com.cottongallery.backend.item.domain.Discount;
import com.cottongallery.backend.item.domain.Item;
import com.cottongallery.backend.item.dto.request.ItemCreateRequest;
import com.cottongallery.backend.item.dto.request.ItemUpdateRequest;
import com.cottongallery.backend.item.dto.response.ItemListResponse;
import com.cottongallery.backend.item.exception.DiscountNotFoundException;
import com.cottongallery.backend.item.exception.ItemNotFoundException;
import com.cottongallery.backend.item.repository.DiscountRepository;
import com.cottongallery.backend.item.repository.ItemRepository;
import com.cottongallery.backend.item.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final DiscountRepository discountRepository;

    @Override
    public Long createItem(ItemCreateRequest itemCreateRequest, Long discountId) {
        Discount discount = null;

        if (discountId != null) {
            discount = discountRepository.findById(discountId).orElseThrow(DiscountNotFoundException::new);
        }

        Item item = Item.createItem(itemCreateRequest.getName(),
                itemCreateRequest.getPrice(),
                itemCreateRequest.getStockQuantity(),
                itemCreateRequest.getContent(),
                discount);

        Item savedItem = itemRepository.save(item);

        return savedItem.getId();
    }

    @Override
    @Transactional(readOnly = true)
    public Slice<ItemListResponse> getItemResponses(Pageable pageable) {
        return itemRepository
                .findAll(pageable)
                .map(ItemListResponse::fromItem);
    }

    @Override
    public Long updateItem(ItemUpdateRequest itemUpdateRequest, Long itemId, Long discountId) {
        Discount discount = discountRepository.findById(discountId).orElse(null);

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
