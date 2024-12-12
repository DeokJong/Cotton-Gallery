package com.cottongallery.backend.order.service;

import com.cottongallery.backend.common.dto.AccountSessionDTO;
import com.cottongallery.backend.order.domain.CartItem;
import com.cottongallery.backend.order.dto.request.CartItemCreatRequest;
import com.cottongallery.backend.order.service.impl.QuantityChangeType;

import java.util.List;

public interface CartItemCommandService {
    Long createCartItem(AccountSessionDTO accountSessionDTO, CartItemCreatRequest cartItemCreatRequest);
    void updateCartItemQuantity(AccountSessionDTO accountSessionDTO, Long cartItemId, QuantityChangeType quantityChangeType);
    void deleteCartItem(AccountSessionDTO accountSessionDTO, Long cartItemId);
    void deleteAllCartItem(List<CartItem> cartItemList);
}
