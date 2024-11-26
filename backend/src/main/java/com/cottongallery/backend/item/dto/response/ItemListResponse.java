package com.cottongallery.backend.item.dto.response;

import com.cottongallery.backend.common.dto.PageInfo;
import lombok.*;

import java.util.List;

@Getter @Setter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ItemListResponse {
    private List<ItemResponse> items;
    private PageInfo pageInfo;

    public static ItemListResponse fromItemResponse(List<ItemResponse> itemResponses, PageInfo pageInfo) {
        return new ItemListResponse(itemResponses, pageInfo);
    }
}
