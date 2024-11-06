package com.cottongallery.backend.item.dto.request;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter @Setter
public class DiscountUpdateRequest {

    @NotBlank
    private String name;

    @NotNull
    @Positive
    @Max(100)
    private BigDecimal discountPercent;

    @NotNull
    @FutureOrPresent
    private LocalDate startDate;

    @NotNull
    @FutureOrPresent
    private LocalDate endDate;
}

