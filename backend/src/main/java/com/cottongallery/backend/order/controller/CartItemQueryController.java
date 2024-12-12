package com.cottongallery.backend.order.controller;

import com.cottongallery.backend.common.argumentResolver.annotation.Login;
import com.cottongallery.backend.common.dto.AccountSessionDTO;
import com.cottongallery.backend.common.dto.Response;
import com.cottongallery.backend.order.controller.api.CartItemQueryApi;
import com.cottongallery.backend.order.dto.response.CartItemListResponse;
import com.cottongallery.backend.order.service.CartItemQueryService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class CartItemQueryController implements CartItemQueryApi {

    private final CartItemQueryService cartItemQueryService;

    @Override
    @GetMapping("/cartItem")
    public ResponseEntity<Response<CartItemListResponse>> retrieveCartItemList(@Login AccountSessionDTO accountSessionDTO) {
        CartItemListResponse cartItems = cartItemQueryService.getCartItemsByCreatedBy(accountSessionDTO.getUsername());

        return new ResponseEntity<>(Response.createResponse(HttpServletResponse.SC_OK, "장바구니 목록 조회에 성공했습니다.", cartItems), HttpStatus.OK);
    }
}
