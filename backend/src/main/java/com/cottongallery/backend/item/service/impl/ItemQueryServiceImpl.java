package com.cottongallery.backend.item.service.impl;

import com.cottongallery.backend.item.constants.ImageType;
import com.cottongallery.backend.item.domain.Discount;
import com.cottongallery.backend.item.constants.DiscountStatus;
import com.cottongallery.backend.item.domain.Item;
import com.cottongallery.backend.item.constants.ItemStatus;
import com.cottongallery.backend.item.dto.response.ItemDetailResponse;
import com.cottongallery.backend.item.dto.response.ItemResponse;
import com.cottongallery.backend.item.exception.ItemNotFoundException;
import com.cottongallery.backend.item.repository.ItemRepository;
import com.cottongallery.backend.item.service.ItemQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemQueryServiceImpl implements ItemQueryService {

    private final ItemRepository itemRepository;

    @Override
    public Slice<ItemResponse> getItemResponses(Pageable pageable, String keyword) {
        Slice<Item> items;

        if (keyword == null || keyword.trim().isEmpty()) {
            items = itemRepository
                    .findAllByItemStatus(pageable, ItemStatus.ACTIVE);
        }
        else {
            items = itemRepository
                    .findAllByItemStatusAndNameContaining(pageable, ItemStatus.ACTIVE, keyword);
        }
        return items.map(this::filterDiscountAndConvertToItemResponse);
    }

    @Override
    public Item getItemEntityById(Long itemId) {
        return itemRepository
                .findById(itemId)
                .orElseThrow(ItemNotFoundException::new);
    }

    @Override
    public boolean isItemRelatedToImage(String itemImagePath, ImageType imageType) {
        if (imageType == ImageType.ITEM_IMAGE)
            return itemRepository.existsByItemImagePath(itemImagePath);

        return itemRepository.existsByItemInfoImagePath(itemImagePath);
    }

    @Override
    public ItemDetailResponse getItemDetailResponse(Long itemId) {
        Item item = getItemEntityById(itemId);

        return filterDiscountAndConvertToItemDetailResponse(item);
    }

    private ItemResponse filterDiscountAndConvertToItemResponse(Item item) {
        Discount discount = item.getDiscount();

        if (discount == null || discount.getDiscountStatus() != DiscountStatus.ACTIVE || discount.getEndDate().isBefore(LocalDate.now())) {
            discount = null;
        }

        return ItemResponse.fromItem(item, discount);
    }

    private ItemDetailResponse filterDiscountAndConvertToItemDetailResponse(Item item) {
        Discount discount = item.getDiscount();

        if (discount == null || discount.getDiscountStatus() != DiscountStatus.ACTIVE || discount.getEndDate().isBefore(LocalDate.now())) {
            discount = null;
        }

        return ItemDetailResponse.fromItem(item, discount);
    }

}
