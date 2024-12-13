package com.cottongallery.backend.item.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ItemUpdateRequest {
    @NotBlank
    private String name;

    @Positive
    private Integer price;

    @PositiveOrZero
    private Integer stockQuantity;
}
