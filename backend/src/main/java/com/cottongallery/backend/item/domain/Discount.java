package com.cottongallery.backend.item.domain;

import com.cottongallery.backend.common.domain.base.BaseEntity;
import com.cottongallery.backend.item.constants.DiscountStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Discount extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "discount_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private BigDecimal discountPercent;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private DiscountStatus discountStatus;

    private Discount(String name, BigDecimal discountPercent, LocalDate startDate, LocalDate endDate, DiscountStatus discountStatus) {
        this.name = name;
        this.discountPercent = discountPercent;
        this.startDate = startDate;
        this.endDate = endDate;
        this.discountStatus = discountStatus;
    }

    public static Discount createDiscount(String name, BigDecimal discountPercent, LocalDate startDate, LocalDate endDate, DiscountStatus discountStatus) {
        return new Discount(name, discountPercent, startDate, endDate, discountStatus);
    }

    public void update(String name, BigDecimal discountPercent, LocalDate startDate, LocalDate endDate) {
        this.name = name;
        this.discountPercent = discountPercent;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public void changeDiscountStatus(DiscountStatus discountStatus) {
        this.discountStatus = discountStatus;
    }
}
