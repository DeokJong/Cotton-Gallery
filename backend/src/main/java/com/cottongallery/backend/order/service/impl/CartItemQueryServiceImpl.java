package com.cottongallery.backend.order.service.impl;

import com.cottongallery.backend.order.domain.CartItem;
import com.cottongallery.backend.order.dto.response.CartItemListResponse;
import com.cottongallery.backend.order.dto.response.CartItemResponse;
import com.cottongallery.backend.order.repository.CartItemRepository;
import com.cottongallery.backend.order.service.CartItemQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CartItemQueryServiceImpl implements CartItemQueryService {

    private final CartItemRepository cartItemRepository;

    @Override
    public CartItem getCartItemEntityByIdCreatedBy(Long cartItemId, String createdBy) {
        return cartItemRepository.findByIdAndCreatedBy(cartItemId, createdBy).orElseThrow(RuntimeException::new);
    }

    @Override
    public CartItemListResponse getCartItemsByCreatedBy(String createdBy) {
        return new CartItemListResponse(cartItemRepository
                .findAllByCreatedBy(createdBy).stream()
                .map(CartItemResponse::fromCartItem)
                .toList());
    }
}
