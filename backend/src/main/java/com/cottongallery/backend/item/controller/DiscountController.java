package com.cottongallery.backend.item.controller;

import com.cottongallery.backend.common.dto.PageInfo;
import com.cottongallery.backend.common.dto.Response;
import com.cottongallery.backend.common.exception.InvalidRequestException;
import com.cottongallery.backend.item.dto.request.DiscountCreateRequest;
import com.cottongallery.backend.item.dto.request.DiscountUpdateRequest;
import com.cottongallery.backend.item.dto.response.DiscountListResponse;
import com.cottongallery.backend.item.dto.response.DiscountResponse;
import com.cottongallery.backend.item.service.impl.DiscountService;
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
@RequestMapping("/api/discounts")
@RequiredArgsConstructor
public class DiscountController {

    private final DiscountService discountService;

    @PostMapping
    public ResponseEntity<Response<?>> addDiscount(@Validated @RequestBody DiscountCreateRequest discountCreateRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InvalidRequestException("할인 생성 요청 값이 올바르지 않습니다. 다시 확인해 주세요.", bindingResult);
        }

        discountService.createDiscount(discountCreateRequest);

        log.info("할인 생성 요청 완료: name={}", discountCreateRequest.getName());

        return new ResponseEntity<>(Response.createResponseWithoutData(HttpServletResponse.SC_OK, "할인 생성에 성공했습니다."), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Response<DiscountListResponse>> retrieveDiscounts(@RequestParam(defaultValue = "1") int page) {
        PageRequest pageRequest = PageRequest.of(page - 1, 10, Sort.by(Sort.Direction.DESC, "createdBy"));

        Slice<DiscountResponse> discounts = discountService.getDiscounts(pageRequest);

        List<DiscountResponse> content = discounts.getContent();
        PageInfo pageInfo = new PageInfo(page, discounts.hasNext(), discounts.hasPrevious());

        DiscountListResponse discountListResponse = new DiscountListResponse(pageInfo, content);

        log.info("할인 리스트 조회 완료: size={}", content.size());

        return new ResponseEntity<>(Response.createResponse(HttpServletResponse.SC_OK, "할인 조회에 성공했습니다.", discountListResponse), HttpStatus.OK);
    }

    @PatchMapping("/{discountId}")
    public ResponseEntity<Response<?>> editDiscount(@PathVariable Long discountId,
                                                 @Validated @RequestBody DiscountUpdateRequest discountUpdateRequest,
                                                 BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InvalidRequestException("할인 수정 요청 값이 올바르지 않습니다. 다시 확인해 주세요.", bindingResult);
        }

        discountService.updateDiscount(discountId, discountUpdateRequest);

        log.info("할인 수정 요청 완료: discountId={}", discountId);

        return new ResponseEntity<>(Response.createResponseWithoutData(HttpServletResponse.SC_OK, "할인 " + discountId + " 수정에 성공했습니다."), HttpStatus.OK);
    }

    @DeleteMapping("/{discountId}")
    public ResponseEntity<Response<?>> removeDiscount(@PathVariable Long discountId) {
        discountService.deleteDiscount(discountId);

        log.info("할인 삭제 요청 완료: discountId={}", discountId);

        return new ResponseEntity<>(Response.createResponseWithoutData(HttpServletResponse.SC_OK, "할인 " + discountId + " 삭제에 성공했습니다."), HttpStatus.OK);
    }
}
