package com.cottongallery.backend.order.domain;

import com.cottongallery.backend.auth.domain.Account;
import com.cottongallery.backend.common.domain.base.BaseEntity;
import com.cottongallery.backend.item.domain.Item;
import com.cottongallery.backend.order.service.impl.QuantityChangeType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class CartItem extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "cart_item_id")
    private Long id;

    @Column(nullable = false)
    private int quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    private CartItem(int quantity, Account account, Item item) {
        this.quantity = quantity;
        this.account = account;
        this.item = item;
    }

    public static CartItem createCartItem(int quantity, Account account, Item item) {
        return new CartItem(quantity, account, item);
    }

    public void updateQuantity(QuantityChangeType quantityChangeType) {
        if (quantityChangeType == QuantityChangeType.INCREASE)
            quantity++;
        else {
            if (quantity > 0) {
                quantity--;
            }
        }
    }
}
