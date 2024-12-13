package com.cottongallery.backend.item.service.impl;

import com.cottongallery.backend.item.constants.ImageType;
import com.cottongallery.backend.item.domain.Discount;
import com.cottongallery.backend.item.domain.Item;
import com.cottongallery.backend.item.constants.ItemStatus;
import com.cottongallery.backend.item.dto.request.ItemCreateRequest;
import com.cottongallery.backend.item.dto.request.ItemUpdateRequest;
import com.cottongallery.backend.item.repository.DiscountRepository;
import com.cottongallery.backend.item.repository.ItemRepository;
import com.cottongallery.backend.item.service.ImageService;
import com.cottongallery.backend.item.service.ItemCommandService;
import com.cottongallery.backend.item.service.ItemQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ItemCommandServiceImpl implements ItemCommandService {

    private final ItemQueryService itemQueryService;
    private final ImageService imageService;

    private final DiscountRepository discountRepository;
    private final ItemRepository itemRepository;

    @Override
    public Long createItem(ItemCreateRequest itemCreateRequest, Long discountId, MultipartFile itemImage, MultipartFile itemInfoImage) throws IOException {
        Discount discount = Optional.ofNullable(discountId)
                .flatMap(discountRepository::findById)
                .orElse(null);

        String itemImageFullPath = imageService.saveFile(itemImage), itemInfoImageFullPath = imageService.saveFile(itemInfoImage);

        Item item = Item.createItem(itemCreateRequest.getName(),
                itemCreateRequest.getPrice(),
                itemCreateRequest.getStockQuantity(),
                itemImageFullPath,
                itemInfoImageFullPath,
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
                discount);

        return item.getId();
    }

    @Override
    public void updateItemImage(Long itemId, MultipartFile itemImage, ImageType imageType) throws IOException {
        Item item = itemQueryService.getItemEntityById(itemId);

        if (imageType == ImageType.ITEM_IMAGE) {
            imageService.deleteImage(item.getItemImagePath());
            String fullPath = imageService.saveFile(itemImage);
            item.changeItemImagePath(fullPath);
        }
        else {
            imageService.deleteImage(item.getItemInfoImagePath());
            String fullPath = imageService.saveFile(itemImage);
            item.changeItemInfoImagePath(fullPath);
        }
    }

    @Override
    public void deleteItem(Long itemId) throws IOException {
        Item item = itemQueryService.getItemEntityById(itemId);

        item.changeItemStatus(ItemStatus.DELETED);

        imageService.deleteImage(item.getItemImagePath());
        imageService.deleteImage(item.getItemInfoImagePath());
    }
}
