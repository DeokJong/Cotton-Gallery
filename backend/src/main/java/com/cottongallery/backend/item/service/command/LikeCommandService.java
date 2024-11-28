package com.cottongallery.backend.item.service.command;

import com.cottongallery.backend.common.dto.AccountSessionDTO;

public interface LikeCommandService {
    Long createLike(AccountSessionDTO accountSessionDTO, Long itemId);
    void deleteLike(AccountSessionDTO accountSessionDTO, Long itemId);
}
