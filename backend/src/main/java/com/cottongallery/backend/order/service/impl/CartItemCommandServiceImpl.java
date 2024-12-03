package com.cottongallery.backend.order.service.impl;

import com.cottongallery.backend.auth.domain.Account;
import com.cottongallery.backend.auth.service.AccountQueryService;
import com.cottongallery.backend.common.dto.AccountSessionDTO;
import com.cottongallery.backend.item.domain.Item;
import com.cottongallery.backend.item.service.ItemQueryService;
import com.cottongallery.backend.order.domain.CartItem;
import com.cottongallery.backend.order.dto.request.CartItemCreatRequest;
import com.cottongallery.backend.order.repository.CartItemRepository;
import com.cottongallery.backend.order.service.CartItemCommandService;
import com.cottongallery.backend.order.service.CartItemQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CartItemCommandServiceImpl implements CartItemCommandService {

    private final CartItemRepository cartItemRepository;

    private final CartItemQueryService cartItemQueryService;
    private final AccountQueryService accountQueryService;
    private final ItemQueryService itemQueryService;

    @Override
    public Long createCartItem(AccountSessionDTO accountSessionDTO, CartItemCreatRequest cartItemCreatRequest) {
        Account account = accountQueryService.getAccountEntityByUsername(accountSessionDTO.getUsername());
        Item item = itemQueryService.getItemEntityById(cartItemCreatRequest.getItemId());

        CartItem savedCartItem = cartItemRepository.save(CartItem.createCartItem(cartItemCreatRequest.getQuantity(), account, item));

        return savedCartItem.getId();
    }

    @Override
    public void updateCartItemQuantity(AccountSessionDTO accountSessionDTO, Long cartItemId, QuantityChangeType quantityChangeType) {
        // QuantityChangeType 수량 증가 감소 여부
        CartItem cartItem = cartItemQueryService.getCartItemEntityByIdCreatedBy(cartItemId, accountSessionDTO.getUsername());

        cartItem.updateQuantity(quantityChangeType);
    }

    @Override
    public void deleteCartItem(AccountSessionDTO accountSessionDTO, Long cartItemId) {
        CartItem cartItem = cartItemQueryService.getCartItemEntityByIdCreatedBy(cartItemId, accountSessionDTO.getUsername());

        cartItemRepository.delete(cartItem);
    }

    @Override
    public void deleteAllCartItem(List<CartItem> cartItemList) {
        cartItemRepository.deleteAll(cartItemList);
    }
}
