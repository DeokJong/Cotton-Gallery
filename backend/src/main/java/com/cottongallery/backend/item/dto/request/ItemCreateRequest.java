package com.cottongallery.backend.item.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ItemCreateRequest {
    private String name;
    private Integer price;
    private Integer stockQuantity;
    private String content;
}
