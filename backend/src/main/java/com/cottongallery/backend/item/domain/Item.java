package com.cottongallery.backend.item.domain;

import com.cottongallery.backend.domain.base.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "discount_id")
    private Discount discount;

    private Item(String name, Integer price, Integer stockQuantity, String content, Discount discount) {
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.content = content;
        this.discount = discount;
    }

    public static Item createItemWithoutDiscount(String name, Integer price, Integer stockQuantity, String content) {
        return new Item(name, price, stockQuantity, content, null);
    }

    public static Item createItem(String name, Integer price, Integer stockQuantity, String content, Discount discount) {
        return new Item(name, price, stockQuantity, content, discount);
    }

    public void reduceStockQuantity(Integer quantity) {
        stockQuantity = stockQuantity - quantity;
    }

    public void update(String name, Integer price, Integer stockQuantity, String content, Discount discount) {
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.content = content;
        this.discount = discount;
    }
}
