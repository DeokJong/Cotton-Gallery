package com.cottongallery.backend.item.dto.response;

import com.cottongallery.backend.item.domain.Discount;
import com.cottongallery.backend.item.domain.Item;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Optional;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ItemDetailResponse {
    private Long itemId;
    private String name;
    private Integer price;
    private Integer stockQuantity;
    private String itemImage;
    private String itemInfoImage;
    private int likeCount;
    private boolean likedByMe;

    private LocalDateTime lastModifiedDate;
    private String lastModifiedBy;
    private DiscountResponse discountResponse;

    public static ItemDetailResponse fromItem(Item item, Discount discount) {
        DiscountResponse discountResponse = Optional.ofNullable(discount)
                .map(DiscountResponse::fromDiscount)
                .orElse(null);

        return new ItemDetailResponse(item.getId(),
                item.getName(),
                item.getPrice(),
                item.getStockQuantity(),
                item.getItemImagePath(),
                item.getItemInfoImagePath(),
                item.getLikeCount(),
                false,
                item.getLastModifiedDate(),
                item.getLastModifiedBy(),
                discountResponse);
    }
}

