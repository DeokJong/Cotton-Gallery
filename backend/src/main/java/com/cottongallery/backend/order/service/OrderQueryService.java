package com.cottongallery.backend.order.service;

import com.cottongallery.backend.auth.domain.Account;
import com.cottongallery.backend.common.dto.AccountSessionDTO;
import com.cottongallery.backend.order.domain.Order;
import com.cottongallery.backend.order.dto.response.OrderListResponse;
import com.cottongallery.backend.order.dto.response.OrderResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;


public interface OrderQueryService {

    /**
     * 특정 주문의 상세 정보를 조회합니다.
     *
     * @param orderId 조회할 주문의 ID
     * @param accountSessionDTO 로그인한 사용자의 세션 정보
     * @return 사용자 주문의 상세 정보
     */
    OrderResponse getOrderResponse(Long orderId, AccountSessionDTO accountSessionDTO);

    /**
     * 사용자의 주문 목록을 페이징 형태로 조회합니다.
     *
     * @param accountSessionDTO 로그인한 사용자의 세션 정보
     * @param pageable 페이지 정보를 담은 객체
     * @return 사용자의 주문 목록
     */
    Slice<OrderListResponse> getOrderListResponses(AccountSessionDTO accountSessionDTO, Pageable pageable);

    /**
     * 특정 사용자 계정과 주문 ID를 기반으로 주문 엔티티 조회합니다.
     *
     * @param account 조회할 사용자의 엔티티
     * @param orderId 조회할 주문의 ID
     * @return 조회된 주문 엔티티
     */
    Order getOrderEntityByAccountAndId(Account account, Long orderId);
}
