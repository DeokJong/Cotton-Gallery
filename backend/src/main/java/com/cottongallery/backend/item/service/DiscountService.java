package com.cottongallery.backend.item.service;

import com.cottongallery.backend.item.domain.Discount;
import com.cottongallery.backend.item.dto.request.DiscountCreateRequest;
import com.cottongallery.backend.item.dto.request.DiscountUpdateRequest;
import com.cottongallery.backend.item.dto.response.DiscountResponse;
import com.cottongallery.backend.item.exception.DiscountNotFoundException;
import com.cottongallery.backend.item.repository.DiscountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class DiscountService {

    private final DiscountRepository discountRepository;

    public Long createDiscount(DiscountCreateRequest discountCreateRequest) {
        Discount discount = Discount.createDiscount(discountCreateRequest.getName(),
                discountCreateRequest.getDiscountPercent(),
                discountCreateRequest.getStartDate(),
                discountCreateRequest.getEndDate());

        Discount savedDiscount = discountRepository.save(discount);

        log.debug("할인 생성 성공: discountName={}", savedDiscount.getName());

        return savedDiscount.getId();
    }

    public Long updateDiscount(Long discountId, DiscountUpdateRequest discountUpdateRequest) {
        Discount discount = discountRepository
                .findById(discountId)
                .orElseThrow(DiscountNotFoundException::new);

        discount.update(discountUpdateRequest.getName(),
                discountUpdateRequest.getDiscountPercent(),
                discountUpdateRequest.getStartDate(),
                discountUpdateRequest.getEndDate());

        return discount.getId();
    }

    public Slice<DiscountResponse> getDiscounts(Pageable pageable) {
        return discountRepository
                .findAll(pageable)
                .map(DiscountResponse::fromDiscount);
    }

    public void deleteDiscount(Long discountId) {
        Discount discount = discountRepository
                .findById(discountId)
                .orElseThrow(DiscountNotFoundException::new);

        discountRepository.delete(discount);
    }
}
