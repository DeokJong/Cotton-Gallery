package com.cottongallery.backend.item.dto.response;

import com.cottongallery.backend.item.domain.Discount;
import com.cottongallery.backend.item.domain.Item;
import lombok.*;

import java.util.Optional;

@Getter @Setter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ItemResponse {
    private Long itemId;
    private String name;
    private Integer price;
    private Integer stockQuantity;
    private int likeCount;
    private boolean likedByMe;

    private DiscountResponse discountResponse;

    public static ItemResponse fromItem(Item item, Discount discount) {
        DiscountResponse discountResponse = Optional.ofNullable(discount)
                .map(DiscountResponse::fromDiscount)
                .orElse(null);

        return new ItemResponse(item.getId(),
                item.getName(),
                item.getPrice(),
                item.getStockQuantity(),
                item.getLikeCount(),
                false,
                discountResponse);
    }
}
