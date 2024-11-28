package com.cottongallery.backend.item.service;

import com.cottongallery.backend.common.dto.AccountSessionDTO;

public interface LikeQueryService {
    boolean isLikedByAccount(AccountSessionDTO accountId, Long itemId);
}
