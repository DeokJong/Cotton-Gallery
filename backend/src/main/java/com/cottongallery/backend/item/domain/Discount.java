package com.cottongallery.backend.item.domain;

import com.cottongallery.backend.domain.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
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

    private Discount(String name, BigDecimal discountPercent, LocalDate startDate, LocalDate endDate) {
        this.name = name;
        this.discountPercent = discountPercent;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public static Discount createDiscount(String name, BigDecimal discountPercent, LocalDate startDate, LocalDate endDate) {
        return new Discount(name, discountPercent, startDate, endDate);
    }

    public void update(String name, BigDecimal discountPercent, LocalDate startDate, LocalDate endDate) {
        this.name = name;
        this.discountPercent = discountPercent;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
