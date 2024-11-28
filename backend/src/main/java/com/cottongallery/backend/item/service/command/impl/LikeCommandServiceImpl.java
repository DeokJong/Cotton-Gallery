package com.cottongallery.backend.item.service.command.impl;

import com.cottongallery.backend.auth.domain.Account;
import com.cottongallery.backend.auth.service.query.AccountQueryService;
import com.cottongallery.backend.common.dto.AccountSessionDTO;
import com.cottongallery.backend.item.domain.Item;
import com.cottongallery.backend.item.domain.Like;
import com.cottongallery.backend.item.domain.LikeStatus;
import com.cottongallery.backend.item.repository.LikeRepository;
import com.cottongallery.backend.item.service.command.LikeCommandService;
import com.cottongallery.backend.item.service.query.ItemQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class LikeCommandServiceImpl implements LikeCommandService {

    private final ItemQueryService itemQueryService;
    private final AccountQueryService accountQueryService;

    private final LikeRepository likeRepository;

    @Override
    public Long createLike(AccountSessionDTO accountSessionDTO, Long itemId) {
        // 엔티티 조회
        Item item = itemQueryService.getItemEntityById(itemId);
        Account account = accountQueryService.getAccountEntityByUsername(accountSessionDTO.getUsername());

        // 좋아요 찾거나 생성
        Like like = likeRepository.findByAccountAndItem(account, item)
                .orElseGet(() -> createNewLike(account, item));

        // 좋아요 상태 변경
        like.changeLikeStatus(LikeStatus.ACTIVE);

        // 저장 후 ID 반환
        return likeRepository.save(like).getId();
    }

    @Override
    public void deleteLike(AccountSessionDTO accountSessionDTO, Long itemId) {
        // item 엔티티 조회
        Item item = itemQueryService.getItemEntityById(itemId);

        // 사용자 엔티티 조회
        Account account = accountQueryService.getAccountEntityByUsername(accountSessionDTO.getUsername());

        Like like = likeRepository.findByAccountAndItem(account, item).orElseThrow(RuntimeException::new);

        like.changeLikeStatus(LikeStatus.INACTIVE);
    }

    private Like createNewLike(Account account, Item item) {
        return Like.createLike(account, item, LikeStatus.ACTIVE);
    }
}
