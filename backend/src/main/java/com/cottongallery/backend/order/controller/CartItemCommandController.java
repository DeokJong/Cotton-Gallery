package com.cottongallery.backend.order.controller;

import com.cottongallery.backend.common.argumentResolver.annotation.Login;
import com.cottongallery.backend.common.dto.AccountSessionDTO;
import com.cottongallery.backend.common.dto.Response;
import com.cottongallery.backend.order.dto.request.CartItemCreatRequest;
import com.cottongallery.backend.order.service.CartItemCommandService;
import com.cottongallery.backend.order.service.impl.QuantityChangeType;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class CartItemCommandController {
    private final CartItemCommandService cartItemCommandService;

    // 장바구니 등록
    @PostMapping("/cartItem")
    public ResponseEntity<Response<?>> addCartItem(@Login AccountSessionDTO accountSessionDTO, @RequestBody CartItemCreatRequest cartItemCreatRequest) {
        cartItemCommandService.createCartItem(accountSessionDTO, cartItemCreatRequest);

        return new ResponseEntity<>(Response.createResponseWithoutData(HttpServletResponse.SC_CREATED, "장바구니 상품 등록에 성공했습니다."), HttpStatus.CREATED);
    }

    // 장바구니 수량 변경
    @PatchMapping("/cartItem/{cartItemId}")
    public ResponseEntity<Response<?>> updateCartItemQuantity(@Login AccountSessionDTO accountSessionDTO,
                                                              @PathVariable Long cartItemId,
                                                              @RequestParam QuantityChangeType quantityChangeType) {
        cartItemCommandService.updateCartItemQuantity(accountSessionDTO, cartItemId, quantityChangeType);

        return new ResponseEntity<>(Response.createResponseWithoutData(HttpServletResponse.SC_OK, "장바구니 상품 수량 변경에 성공했습니다."), HttpStatus.OK);
    }
}
