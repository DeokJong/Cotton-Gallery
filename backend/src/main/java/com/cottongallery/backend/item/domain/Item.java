package com.cottongallery.backend.item.domain;

import com.cottongallery.backend.common.domain.base.BaseEntity;
import com.cottongallery.backend.item.constants.ItemStatus;
import com.cottongallery.backend.order.exception.NotEnoughStockQuantityException;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Formula;

import static jakarta.persistence.FetchType.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Item extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer price;

    @Column(nullable = false)
    private Integer stockQuantity;

    private String itemImagePath;

    private String itemInfoImagePath;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "discount_id")
    private Discount discount;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ItemStatus itemStatus;

    @Formula("(SELECT COUNT(l.item_id) FROM likes l WHERE l.item_id = item_id AND l.like_status = 'ACTIVE')")
    private int likeCount;

    private Item(String name, Integer price, Integer stockQuantity, String itemImagePath, String itemInfoImagePath, ItemStatus itemStatus,Discount discount) {
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.itemImagePath = itemImagePath;
        this.itemInfoImagePath = itemInfoImagePath;
        this.discount = discount;
        this.itemStatus = itemStatus;
    }

//    public static Item createItemWithoutDiscount(String name, Integer price, Integer stockQuantity, String content) {
//        return new Item(name, price, stockQuantity, content, null);
//    }

    public static Item createItem(String name, Integer price, Integer stockQuantity, String itemImagePath, String itemInfoImagePath, ItemStatus itemStatus, Discount discount) {
        return new Item(name, price, stockQuantity, itemImagePath, itemInfoImagePath, itemStatus, discount);
    }

    public void reduceStockQuantity(Integer quantity) {
        if (stockQuantity - quantity < 0) {
            throw new NotEnoughStockQuantityException("재고가 부족합니다. 현재 재고: " + stockQuantity);
        }

        stockQuantity = stockQuantity - quantity;
    }

    public void addStockQuantity(Integer stockQuantity) {
        this.stockQuantity += stockQuantity;
    }

    public void update(String name, Integer price, Integer stockQuantity, Discount discount) {
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.discount = discount;
    }

    public void changeItemStatus(ItemStatus itemStatus) {
        this.itemStatus = itemStatus;
    }

    public void changeItemImagePath(String itemImagePath) {
        this.itemImagePath = itemImagePath;
    }

    public void changeItemInfoImagePath(String itemInfoImagePath) {
        this.itemInfoImagePath = itemInfoImagePath;
    }
}
