package com.cottongallery.backend.order.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderListResponse {
    private Long orderId;
    private LocalDateTime orderDate;
    private String deliveryStatus;

    private List<OrderItemResponse> orderItems;
}
