package com.cottongallery.backend.order.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CartItemCreatRequest {
    private int quantity;
    private Long itemId;
}
