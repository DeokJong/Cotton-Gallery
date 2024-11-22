package com.cottongallery.backend.order.dto.response;

import com.cottongallery.backend.order.domain.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemResponse {
    private Long orderItemId;
    private String itemName;
    private Integer orderPrice;
    private Integer count;
    private BigDecimal discountPercent;

    public static OrderItemResponse fromOrderItem(OrderItem orderItem) {
        return new OrderItemResponse(orderItem.getId(), orderItem.getItem().getName(), orderItem.getOrderPrice(), orderItem.getCount(), orderItem.getDiscountPercent());
    }
}
