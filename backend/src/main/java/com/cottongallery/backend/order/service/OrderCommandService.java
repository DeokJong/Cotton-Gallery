package com.cottongallery.backend.order.service;

import com.cottongallery.backend.auth.exception.account.AccountNotFoundException;
import com.cottongallery.backend.auth.exception.address.AddressNotFoundException;
import com.cottongallery.backend.common.dto.AccountSessionDTO;
import com.cottongallery.backend.item.exception.ItemNotFoundException;
import com.cottongallery.backend.order.dto.request.OrderItemCreateRequest;
import com.cottongallery.backend.order.exception.OrderNotFoundException;

import java.util.List;

public interface OrderCommandService {

    /**
     * 새로운 주문을 생성합니다.
     *
     * @param accountSessionDTO 로그인한 사용자의 세션 정보
     * @param orderItemCreateRequestList 주문 항목을 담은 리스트
     * @param addressId 주문 배송지의 ID
     * @return 생성된 주문의 ID
     * @throws AccountNotFoundException 사용자를 찾을 수 없는 경우 발생.
     * @throws ItemNotFoundException 상품을 찾을 수 없는 경우 발생.
     * @throws AddressNotFoundException  주소를 찾을 수 없는 경우 발생.
     */
    Long createOrder(AccountSessionDTO accountSessionDTO, List<OrderItemCreateRequest> orderItemCreateRequestList, Long addressId);

    /**
     * 특정 주문을 취소합니다.
     *
     * @param orderId 취소할 주문의 ID
     * @param accountSessionDTO 로그인한 사용자의 세션 정보
     * @throws AccountNotFoundException 사용자를 찾을 수 없는 경우 발생.
     * @throws OrderNotFoundException 주문을 찾을 수 없는 경우 발생.
     */
    void cancelOrder(Long orderId, AccountSessionDTO accountSessionDTO);
}
