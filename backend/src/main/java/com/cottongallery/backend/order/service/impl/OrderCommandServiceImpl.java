package com.cottongallery.backend.order.service.impl;

import com.cottongallery.backend.auth.domain.Account;
import com.cottongallery.backend.auth.domain.Address;
import com.cottongallery.backend.auth.service.AccountQueryService;
import com.cottongallery.backend.auth.service.AddressQueryService;
import com.cottongallery.backend.common.dto.AccountSessionDTO;
import com.cottongallery.backend.item.domain.Discount;
import com.cottongallery.backend.item.domain.DiscountStatus;
import com.cottongallery.backend.item.domain.Item;
import com.cottongallery.backend.item.service.ItemQueryService;
import com.cottongallery.backend.order.domain.Order;
import com.cottongallery.backend.order.domain.OrderItem;
import com.cottongallery.backend.order.dto.request.OrderItemCreateRequest;
import com.cottongallery.backend.order.repository.OrderRepository;
import com.cottongallery.backend.order.service.OrderCommandService;
import com.cottongallery.backend.order.service.OrderQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class OrderCommandServiceImpl implements OrderCommandService {

    // query
    private final AccountQueryService accountQueryService;
    private final AddressQueryService addressQueryService;
    private final ItemQueryService itemQueryService;
    private final OrderQueryService orderQueryService;

    // repository
    private final OrderRepository orderRepository;

    @Override
    public Long createOrder(AccountSessionDTO accountSessionDTO, List<OrderItemCreateRequest> orderItemCreateRequestList, Long addressId) {
        Account account = accountQueryService.getAccountEntityByUsername(accountSessionDTO.getUsername());

        Address address = addressQueryService.getAddressEntityByIdAndUsername(addressId, accountSessionDTO.getUsername());

        List<OrderItem> orderItemList = createOrderItem(orderItemCreateRequestList);

        Order order = Order.createOrder(account, address, null, orderItemList);
        Order savedOrder = orderRepository.save(order);

        log.debug("주문 생성 성공: id={}", savedOrder.getId());

        return savedOrder.getId();
    }

    @Override
    public void cancelOrder(Long orderId, AccountSessionDTO accountSessionDTO) {
        Account account = accountQueryService.getAccountEntityByUsername(accountSessionDTO.getUsername());

        Order order = orderQueryService.getOrderEntityByAccountAndId(account, orderId);

        order.cancel();
    }

    private List<OrderItem> createOrderItem(List<OrderItemCreateRequest> orderItemCreateRequestList) {
        List<OrderItem> orderItemList = new ArrayList<>();

        for (OrderItemCreateRequest orderItemCreateRequest : orderItemCreateRequestList) {
            Item item = itemQueryService.getItemEntityById(orderItemCreateRequest.getItemId());

            BigDecimal discountPercent = Optional.ofNullable(item.getDiscount())
                    .filter(discount -> discount.getDiscountStatus() == DiscountStatus.ACTIVE)
                    .filter(discount -> discount.getEndDate() == null || !discount.getEndDate().isBefore(LocalDate.now()))
                    .map(Discount::getDiscountPercent)
                    .orElse(null);

            OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), discountPercent, orderItemCreateRequest.getCount());

            orderItemList.add(orderItem);
        }

        return orderItemList;
    }
}
