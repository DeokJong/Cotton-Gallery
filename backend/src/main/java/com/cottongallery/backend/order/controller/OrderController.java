package com.cottongallery.backend.order.controller;

import com.cottongallery.backend.common.argumentResolver.annotation.Login;
import com.cottongallery.backend.common.dto.AccountSessionDTO;
import com.cottongallery.backend.common.dto.ListResponse;
import com.cottongallery.backend.common.dto.PageInfo;
import com.cottongallery.backend.common.dto.Response;
import com.cottongallery.backend.common.exception.InvalidRequestException;
import com.cottongallery.backend.order.dto.request.OrderCreateRequest;
import com.cottongallery.backend.order.dto.response.OrderListResponse;
import com.cottongallery.backend.order.dto.response.OrderResponse;
import com.cottongallery.backend.order.service.OrderService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<Response<OrderResponse>> addOrder(@Login AccountSessionDTO accountSessionDTO,
                                                            @RequestBody @Validated OrderCreateRequest orderCreateRequest,
                                                            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new InvalidRequestException("주문 요청이 올바르지 않습니다. 다시 확인해 주세요.", bindingResult);
        }

        Long orderId = orderService.createOrder(accountSessionDTO, orderCreateRequest.getOrderItemCreateRequestList(), orderCreateRequest.getAddressId());

        return new ResponseEntity<>(Response.createResponse(HttpServletResponse.SC_CREATED, "주문에 성공했습니다.", new OrderResponse(orderId)),
                HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Response<List<OrderListResponse>>> getOrders(@Login AccountSessionDTO accountSessionDTO, @RequestParam(defaultValue = "1") int page) {
        PageRequest pageRequest = PageRequest.of(page - 1, 10, Sort.by(Sort.Direction.DESC, "createdDate"));

        Slice<OrderListResponse> orders = orderService.getOrderListResponses(accountSessionDTO, pageRequest);
        List<OrderListResponse> content = orders.getContent();

        return new ResponseEntity<>(Response.createResponse(HttpServletResponse.SC_OK, "주문 " + page + " 페이지 조회에 성공했습니다.", content), HttpStatus.OK);
    }

    @PatchMapping("/{orderId}")
    public ResponseEntity<Response<?>> cancelOrder(@PathVariable Long orderId, @Login AccountSessionDTO accountSessionDTO) {
        orderService.cancelOrder(orderId, accountSessionDTO);

        return new ResponseEntity<>(Response.createResponseWithoutData(HttpServletResponse.SC_OK, "주문을 성공적으로 취소했습니다."), HttpStatus.OK);
    }
}
