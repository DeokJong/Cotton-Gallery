package com.cottongallery.backend.order.controller.api;

import com.cottongallery.backend.common.argumentResolver.annotation.Login;
import com.cottongallery.backend.common.dto.AccountSessionDTO;
import com.cottongallery.backend.common.dto.Response;
import com.cottongallery.backend.order.dto.request.CartItemCreatRequest;
import com.cottongallery.backend.order.service.impl.QuantityChangeType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "장바구니 관리", description = "장바구니 관련 API")
public interface CartItemCommandApi {

    @Operation(summary = "장바구니 생성", description = "장바구니를 생성합니다.")
    ResponseEntity<Response<?>> addCartItem(@Login AccountSessionDTO accountSessionDTO,
                                            @RequestBody CartItemCreatRequest cartItemCreatRequest);

    @Operation(summary = "장바구니 내 특정 상품 수량 변경", description = "장바구니에 등록된 특정 상품의 수량을 변경합니다.")
    ResponseEntity<Response<?>> updateCartItemQuantity(@Login AccountSessionDTO accountSessionDTO,
                                                       @PathVariable Long cartItemId,
                                                       @RequestParam QuantityChangeType quantityChangeType);
}
