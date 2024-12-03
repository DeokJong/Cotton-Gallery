package com.cottongallery.backend.order.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class OrderCreateFromCartRequest {
    private Long addressId;
    private List<OrderCartItemCreateRequest> orderCartItems;
}
