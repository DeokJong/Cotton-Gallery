package com.cottongallery.backend.item.service.impl;

import com.cottongallery.backend.auth.domain.Account;
import com.cottongallery.backend.auth.service.AccountQueryService;
import com.cottongallery.backend.common.dto.AccountSessionDTO;
import com.cottongallery.backend.item.domain.Item;
import com.cottongallery.backend.item.constants.LikeStatus;
import com.cottongallery.backend.item.repository.LikeRepository;
import com.cottongallery.backend.item.service.ItemQueryService;
import com.cottongallery.backend.item.service.LikeQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LikeQueryServiceImpl implements LikeQueryService {

    private final LikeRepository likeRepository;
    private final ItemQueryService itemQueryService;
    private final AccountQueryService accountQueryService;

    @Override
    public boolean isLikedByAccount(AccountSessionDTO accountSessionDTO, Long itemId) {
        // 엔티티 조회
        Item item = itemQueryService.getItemEntityById(itemId);
        Account account = accountQueryService.getAccountEntityByUsername(accountSessionDTO.getUsername());

        return likeRepository.existsByAccountAndItemAndLikeStatus(account, item, LikeStatus.ACTIVE);
    }
}
