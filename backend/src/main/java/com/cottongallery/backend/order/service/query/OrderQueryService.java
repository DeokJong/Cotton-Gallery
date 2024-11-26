package com.cottongallery.backend.order.service.query;

import com.cottongallery.backend.auth.domain.Account;
import com.cottongallery.backend.common.dto.AccountSessionDTO;
import com.cottongallery.backend.order.domain.Order;
import com.cottongallery.backend.order.dto.response.OrderListResponse;
import com.cottongallery.backend.order.dto.response.OrderResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;


public interface OrderQueryService {

    OrderResponse getOrderResponse(Long orderId, AccountSessionDTO accountSessionDTO);

    Slice<OrderListResponse> getOrderListResponses(AccountSessionDTO accountSessionDTO, Pageable pageable);

    Order getOrderEntityById(Long orderId);

    Order getOrderEntityByAccountAndId(Account account, Long orderId);
}
