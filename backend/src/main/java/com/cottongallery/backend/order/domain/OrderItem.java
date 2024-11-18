package com.cottongallery.backend.order.domain;

import com.cottongallery.backend.item.domain.Item;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

import static jakarta.persistence.FetchType.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem {

    @Id
    @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "item_id", updatable = false, nullable = false)
    private Item item;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "order_id", updatable = false, nullable = false)
    private Order order;

    @Column(updatable = false, nullable = false)
    private Integer orderPrice;

    @Column(updatable = false, nullable = false)
    private BigDecimal discountPercent;

    @Column(updatable = false, nullable = false)
    private Integer count;

    private OrderItem(Item item, Integer orderPrice, BigDecimal discountPercent, Integer count) {
        this.item = item;
        this.orderPrice = orderPrice;
        this.discountPercent = discountPercent;
        this.count = count;
    }

    public static OrderItem createOrderItem(Item item, Integer orderPrice, BigDecimal discountPercent,Integer count) {
        OrderItem orderItem = new OrderItem(item, orderPrice, discountPercent, count);

        item.reduceStockQuantity(count);

        return orderItem;
    }

    /**
     * 주문 취소
     */
    public void cancel() {
        getItem().addStockQuantity(count);
    }

    public void assignOrder(Order order) {
        this.order = order;
    }
}
