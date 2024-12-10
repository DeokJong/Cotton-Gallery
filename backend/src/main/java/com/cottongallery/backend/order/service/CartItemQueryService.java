package com.cottongallery.backend.order.service;

import com.cottongallery.backend.order.domain.CartItem;
import com.cottongallery.backend.order.dto.response.CartItemListResponse;

public interface CartItemQueryService {
    CartItem getCartItemEntityByIdCreatedBy(Long cartItemId, String createdBy);
    CartItemListResponse getCartItemsByCreatedBy(String createdBy);
}
