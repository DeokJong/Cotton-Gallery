package com.cottongallery.backend.order.controller.command;

import com.cottongallery.backend.common.argumentResolver.annotation.Login;
import com.cottongallery.backend.common.dto.AccountSessionDTO;
import com.cottongallery.backend.common.dto.Response;
import com.cottongallery.backend.common.exception.InvalidRequestException;
import com.cottongallery.backend.order.controller.command.api.OrderCommandApi;
import com.cottongallery.backend.order.dto.request.OrderCreateRequest;
import com.cottongallery.backend.order.dto.response.OrderCreateResponse;
import com.cottongallery.backend.order.service.command.OrderCommandService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderCommandController implements OrderCommandApi {

    private final OrderCommandService orderCommandService;

    @Override
    @PostMapping
    public ResponseEntity<Response<OrderCreateResponse>> addOrder(@Login AccountSessionDTO accountSessionDTO,
                                                @RequestBody @Validated OrderCreateRequest orderCreateRequest,
                                                            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new InvalidRequestException("주문 요청이 올바르지 않습니다. 다시 확인해 주세요.", bindingResult);
        }

        Long orderId = orderCommandService.createOrder(accountSessionDTO, orderCreateRequest.getOrderItemCreateRequestList(), orderCreateRequest.getAddressId());

        return new ResponseEntity<>(Response.createResponse(HttpServletResponse.SC_CREATED, "주문에 성공했습니다.", new OrderCreateResponse(orderId)),
                HttpStatus.CREATED);
    }

    @Override
    @PatchMapping("/{orderId}")
    public ResponseEntity<Response<?>> cancelOrder(@PathVariable Long orderId, @Login AccountSessionDTO accountSessionDTO) {
        orderCommandService.cancelOrder(orderId, accountSessionDTO);

        return new ResponseEntity<>(Response.createResponseWithoutData(HttpServletResponse.SC_OK, "주문을 성공적으로 취소했습니다."), HttpStatus.OK);
    }
}
