package com.cottongallery.backend.order.service.query.impl;

import com.cottongallery.backend.auth.domain.Account;
import com.cottongallery.backend.auth.service.query.AccountQueryService;
import com.cottongallery.backend.auth.service.query.AddressQueryService;
import com.cottongallery.backend.common.dto.AccountSessionDTO;
import com.cottongallery.backend.common.dto.PageInfo;
import com.cottongallery.backend.order.domain.Order;
import com.cottongallery.backend.order.domain.OrderItem;
import com.cottongallery.backend.order.dto.response.OrderItemResponse;
import com.cottongallery.backend.order.dto.response.OrderListResponse;
import com.cottongallery.backend.order.dto.response.OrderResponse;
import com.cottongallery.backend.order.exception.OrderNotFoundException;
import com.cottongallery.backend.order.repository.OrderRepository;
import com.cottongallery.backend.order.service.query.OrderQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderQueryServiceImpl implements OrderQueryService {

    // query
    private final AccountQueryService accountQueryService;
    private final AddressQueryService addressQueryService;

    // repository
    private final OrderRepository orderRepository;

    @Override
    public OrderResponse getOrderResponse(Long orderId, AccountSessionDTO accountSessionDTO) {
        Account account = accountQueryService.getAccountEntityByUsername(accountSessionDTO.getUsername());
        Order order = getOrderEntityByAccountAndId(account, orderId);

        List<OrderItem> orderItems = order.getOrderItems();

        return OrderResponse.fromOrder(order);
    }

    @Override
    public Slice<OrderListResponse> getOrderListResponses(AccountSessionDTO accountSessionDTO, Pageable pageable) {
        Account account = accountQueryService.getAccountEntityByUsername(accountSessionDTO.getUsername());

        Slice<Order> orders = orderRepository.findByAccount(account, pageable);

        return orders.map(order -> {
            List<OrderItemResponse> orderItems = order.getOrderItems().stream()
                    .map(OrderItemResponse::fromOrderItem)
                    .toList();

            PageInfo pageInfo = new PageInfo(pageable.getPageNumber() + 1, orders.hasNext(), orders.hasPrevious());

            return new OrderListResponse(order.getId(), order.getCreatedDate(), order.getStatus().name(), pageInfo, orderItems);
        });
    }

    @Override
    public Order getOrderEntityByAccountAndId(Account account, Long orderId) {
        return orderRepository
                .findByAccountAndId(account, orderId)
                .orElseThrow(OrderNotFoundException::new);
    }
}
