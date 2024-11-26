package com.cottongallery.backend.order.service.command;

import com.cottongallery.backend.common.dto.AccountSessionDTO;
import com.cottongallery.backend.order.dto.request.OrderItemCreateRequest;

import java.util.List;

public interface OrderCommandService {

    Long createOrder(AccountSessionDTO accountSessionDTO, List<OrderItemCreateRequest> orderItemCreateRequestList, Long addressId);

    void cancelOrder(Long orderId, AccountSessionDTO accountSessionDTO);
}
