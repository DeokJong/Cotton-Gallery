package com.cottongallery.backend.item.domain;

import com.cottongallery.backend.auth.domain.Account;
import com.cottongallery.backend.common.domain.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter
@Table(name = "likes")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Like extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "like_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @Enumerated(EnumType.STRING)
    private LikeStatus likeStatus;

    private Like(Account account, Item item, LikeStatus likeStatus) {
        this.account = account;
        this.item = item;
        this.likeStatus = likeStatus;
    }

    public static Like createLike(Account account, Item item, LikeStatus likeStatus) {
        return new Like(account, item, likeStatus);
    }

    public void changeLikeStatus(LikeStatus likeStatus) {
        this.likeStatus = likeStatus;
    }

}
