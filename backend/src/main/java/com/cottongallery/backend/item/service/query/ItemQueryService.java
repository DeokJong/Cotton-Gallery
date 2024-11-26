package com.cottongallery.backend.item.service.query;

import com.cottongallery.backend.item.domain.Item;
import com.cottongallery.backend.item.dto.response.ItemListResponse;
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
    Slice<ItemListResponse> getItemResponses(Pageable pageable);

    Item getItemEntityById(Long itemId);
}
