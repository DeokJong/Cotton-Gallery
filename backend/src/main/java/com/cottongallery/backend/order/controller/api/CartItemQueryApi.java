package com.cottongallery.backend.order.controller.api;

import com.cottongallery.backend.common.argumentResolver.annotation.Login;
import com.cottongallery.backend.common.dto.AccountSessionDTO;
import com.cottongallery.backend.common.dto.Response;
import com.cottongallery.backend.order.dto.response.CartItemListResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "장바구니 관리", description = "장바구니 관련 API")
public interface CartItemQueryApi {

    @Operation(summary = "장바구니 목록 조회", description = "사용자 정보를 바탕으로 장바구니 목록을 조회합니다.")
    ResponseEntity<Response<CartItemListResponse>> retrieveCartItemList(@Login AccountSessionDTO accountSessionDTO);
}
