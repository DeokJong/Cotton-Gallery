package com.cottongallery.backend.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class PageInfo {
    private int currentPage;
    private boolean hasNext;
    private boolean hasPrevious;
}
