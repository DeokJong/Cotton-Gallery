package com.cottongallery.backend.order.controller.api;

import com.cottongallery.backend.common.argumentResolver.annotation.Login;
import com.cottongallery.backend.common.dto.AccountSessionDTO;
import com.cottongallery.backend.common.dto.Response;
import com.cottongallery.backend.order.dto.request.OrderCreateRequest;
import com.cottongallery.backend.order.dto.response.OrderCreateResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "주문 관리", description = "주문 관련 API")
public interface OrderCommandApi {

    @Operation(summary = "주문 생성", description = "주문을 생성합니다.")
    ResponseEntity<Response<OrderCreateResponse>> addOrder(@Login AccountSessionDTO accountSessionDTO,
                                                           @RequestBody @Validated OrderCreateRequest orderCreateRequest,
                                                           BindingResult bindingResult);

    @Operation(summary = "주문 취소", description = "특정 주문을 취소합니다.")
    ResponseEntity<Response<?>> cancelOrder(@Parameter(description = "취소할 주문의 ID") @PathVariable Long orderId, @Login AccountSessionDTO accountSessionDTO);
}
