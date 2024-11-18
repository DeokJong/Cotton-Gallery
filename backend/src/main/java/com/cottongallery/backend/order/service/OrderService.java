package com.cottongallery.backend.order.service;

import com.cottongallery.backend.auth.domain.Account;
import com.cottongallery.backend.auth.exception.account.AccountNotFoundException;
import com.cottongallery.backend.auth.exception.address.AddressNotFoundException;
import com.cottongallery.backend.auth.repository.AccountRepository;
import com.cottongallery.backend.auth.repository.AddressRepository;
import com.cottongallery.backend.common.constants.OrderStatus;
import com.cottongallery.backend.common.dto.AccountSessionDTO;
import com.cottongallery.backend.item.domain.Discount;
import com.cottongallery.backend.item.domain.Item;
import com.cottongallery.backend.item.exception.ItemNotFoundException;
import com.cottongallery.backend.item.repository.ItemRepository;
import com.cottongallery.backend.order.domain.Address;
import com.cottongallery.backend.order.domain.Order;
import com.cottongallery.backend.order.domain.OrderItem;
import com.cottongallery.backend.order.dto.request.OrderItemCreateRequest;
import com.cottongallery.backend.order.dto.response.OrderItemResponse;
import com.cottongallery.backend.order.dto.response.OrderListResponse;
import com.cottongallery.backend.order.exception.OrderNotFoundException;
import com.cottongallery.backend.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final AccountRepository accountRepository;
    private final AddressRepository addressRepository;
    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;

    public Long createOrder(AccountSessionDTO accountSessionDTO, List<OrderItemCreateRequest> orderItemCreateRequestList, Long addressId) {
        Account account = getAccountByUsername(accountSessionDTO.getUsername());

        Address address = addressRepository.findById(addressId).orElseThrow(AddressNotFoundException::new);

        List<OrderItem> orderItemList = createOrderItem(orderItemCreateRequestList);

        Order order = Order.createOrder(account, address, null, orderItemList);
        Order savedOrder = orderRepository.save(order);

        log.debug("주문 생성 성공: id={}", savedOrder.getId());

        return savedOrder.getId();
    }

    public void cancelOrder(Long orderId) {
        //주문 엔티티 조회
        Order order = orderRepository.findById(orderId).orElseThrow(OrderNotFoundException::new);
        //주문 취소
        order.cancel();
    }

    @Transactional(readOnly = true)
    public Slice<OrderListResponse> getOrderListResponses(AccountSessionDTO accountSessionDTO, Pageable pageable) {
        Account account = getAccountByUsername(accountSessionDTO.getUsername());

        Slice<Order> orders = orderRepository.findByAccount(account, pageable);

        return orders.map(order -> {
            List<OrderItemResponse> orderItems = order.getOrderItems().stream()
                    .map(OrderItemResponse::fromOrderItem)
                    .toList();

            return new OrderListResponse(order.getId(), order.getCreatedDate(), order.getStatus().name(), orderItems);
        });
    }

    public void cancelOrder(Long orderId, AccountSessionDTO accountSessionDTO) {
        Account account = getAccountByUsername(accountSessionDTO.getUsername());

        Order order = orderRepository.findByAccountAndId(account, orderId).orElseThrow(OrderNotFoundException::new);

        order.changeOrderStatus(OrderStatus.CANCEL);
    }

    private Account getAccountByUsername(String username) {
        return accountRepository
                .findByUsername(username)
                .orElseThrow(AccountNotFoundException::new);
    }

    private List<OrderItem> createOrderItem(List<OrderItemCreateRequest> orderItemCreateRequestList) {
        List<OrderItem> orderItemList = new ArrayList<>();

        for (OrderItemCreateRequest orderItemCreateRequest : orderItemCreateRequestList) {
            Item item = itemRepository.findById(orderItemCreateRequest.getItemId()).orElseThrow(ItemNotFoundException::new);

            BigDecimal discountPercent = Optional.ofNullable(item.getDiscount())
                    .map(Discount::getDiscountPercent)
                    .orElse(null);

            OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), discountPercent, orderItemCreateRequest.getCount());

            orderItemList.add(orderItem);
        }

        return orderItemList;
    }
}
