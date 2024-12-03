package com.cottongallery.backend.order.dto.response;

import com.cottongallery.backend.item.domain.Discount;
import com.cottongallery.backend.order.domain.CartItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Optional;

@Setter @Getter
@AllArgsConstructor
@NoArgsConstructor
public class CartItemResponse {
    private Long cartItemId;
    private BigDecimal discountPercent;
    private String name;
    private int price;
    private int quantity;

    public static CartItemResponse fromCartItem(CartItem cartItem) {
        return new CartItemResponse(cartItem.getId(),
                Optional.ofNullable(cartItem.getItem().getDiscount())
                        .map(Discount::getDiscountPercent)
                        .orElse(null),
                cartItem.getItem().getName(),
                cartItem.getItem().getPrice(),
                cartItem.getQuantity());
    }
}
