package com.cottongallery.backend.order.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderCreateRequest {

    private Long addressId;
    List<OrderItemCreateRequest> orderItemCreateRequestList;
}
