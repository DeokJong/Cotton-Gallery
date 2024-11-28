package com.cottongallery.backend.item.repository;

import com.cottongallery.backend.auth.domain.Account;
import com.cottongallery.backend.item.domain.Item;
import com.cottongallery.backend.item.domain.Like;
import com.cottongallery.backend.item.domain.LikeStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findByAccountAndItem(Account account, Item item);
    boolean existsByAccountAndItemAndLikeStatus(Account account, Item item, LikeStatus likeStatus);
}
