package com.cottongallery.backend.item.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter @Setter
public class DiscountUpdateRequest {

    private String name;
    private BigDecimal discountPercent;
    private LocalDate startDate;
    private LocalDate endDate;
}

