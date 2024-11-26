package com.cottongallery.backend.order.controller.query.api;

import com.cottongallery.backend.common.argumentResolver.annotation.Login;
import com.cottongallery.backend.common.dto.AccountSessionDTO;
import com.cottongallery.backend.common.dto.Response;
import com.cottongallery.backend.order.dto.response.OrderListResponse;
import com.cottongallery.backend.order.dto.response.OrderResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Tag(name = "주문 관리", description = "주문 관련 API")
public interface OrderQueryApi {

    @Operation(summary = "주문 상세 조회", description = "특정 주문의 상세 정보를 조회합니다.")
    ResponseEntity<Response<OrderResponse>> retrieveOrder(@Parameter(description = "조회할 주문의 ID") @PathVariable Long orderId, @Login AccountSessionDTO accountSessionDTO);

    @Operation(summary = "주문 목록 조회", description = "사용자의 주문 목록을 페이징 형태로 조회합니다.")
    ResponseEntity<Response<List<OrderListResponse>>> retrieveOrderList(@Login AccountSessionDTO accountSessionDTO,
                                                                        @Parameter(description = "조회할 페이지 번호 (1부터 시작)") @RequestParam(defaultValue = "1") int page);
}
