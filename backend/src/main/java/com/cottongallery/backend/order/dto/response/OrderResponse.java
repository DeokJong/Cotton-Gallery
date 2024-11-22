package com.cottongallery.backend.order.dto.response;

import com.cottongallery.backend.order.domain.Order;
import com.cottongallery.backend.order.domain.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {
    private Long orderId;

    private String zipcode;
    private String street;
    private String detail;

    private LocalDateTime orderDate;
    private String deliveryStatus;

    List<OrderItemResponse> orderItems;

    public static OrderResponse fromOrder(Order order) {
        return new OrderResponse(
                order.getId(),
                order.getDelivery().getZipcode(),
                order.getDelivery().getStreet(),
                order.getDelivery().getDetail(),
                order.getCreatedDate(),
                order.getStatus().name(),
                new ArrayList<>(
                        order.getOrderItems().stream()
                                .map(OrderItemResponse::fromOrderItem)
                                .toList()
                )
        );
    }
}
