package com.cottongallery.backend.item.service;

import com.cottongallery.backend.item.domain.Item;
import com.cottongallery.backend.item.dto.response.ItemResponse;
import com.cottongallery.backend.item.exception.ItemNotFoundException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface ItemQueryService {
    /**
     * pageable 정보에 따라 상품 목록을 가져옵니다.
     *
     * @param pageable 페이지, 정렬 정보를 담고 있는 객체
     * @return 페이지된 상품 목록
     */
    Slice<ItemResponse> getItemResponses(Pageable pageable, String keyword);

    /**
     * 주어진 ID를 통해 아이템 엔티티를 조회합니다.
     *
     * @param itemId 조회할 아이템의 ID
     * @return 조회된 아이템 엔티티
     * @throws ItemNotFoundException 주어진 ID에 해당하는 아이템이 없는 경우
     */
    Item getItemEntityById(Long itemId);

    boolean isItemRelatedToImage(String itemImagePath);
}
