package com.cottongallery.backend.item.dto.response;

import com.cottongallery.backend.common.dto.PageInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@AllArgsConstructor
public class DiscountListResponse {
    private PageInfo pageInfo;
    private List<DiscountResponse> discounts;
}
