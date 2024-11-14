package com.cottongallery.backend.item.dto.response;

import com.cottongallery.backend.item.domain.Discount;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class DiscountResponse {

    private Long discountId;
    private String name;
    private BigDecimal discountPercent;
    private LocalDate startDate;
    private LocalDate endDate;

    public static DiscountResponse fromDiscount(Discount discount) {
        return new DiscountResponse(discount.getId(),
                discount.getName(),
                discount.getDiscountPercent(),
                discount.getStartDate(),
                discount.getEndDate());
    }
}
