package com.cottongallery.backend.item.service;

import com.cottongallery.backend.item.dto.request.ItemCreateRequest;
import com.cottongallery.backend.item.dto.request.ItemUpdateRequest;
import com.cottongallery.backend.item.dto.response.ItemListResponse;
import com.cottongallery.backend.item.exception.DiscountNotFoundException;
import com.cottongallery.backend.item.exception.ItemNotFoundException;
import jakarta.annotation.Nullable;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface ItemService {

    /**
     * 새로운 상품을 만들고, 할인 ID가 있으면 할인 정보를 함께 등록합니다.
     *
     * @param itemCreateRequest 생성할 상품의 정보
     * @param discountId 상품에 등록할 할인 ID
     * @return 생성된 상품의 ID
     *
     * @throws DiscountNotFoundException 해당 ID에 대한 할인 엔티티가 없는 경우 발생
     */
    Long createItem(ItemCreateRequest itemCreateRequest, @Nullable Long discountId);

    /**
     * pageable 정보에 따라 상품 목록을 가져옵니다.
     *
     * @param pageable 페이지, 정렬 정보를 담고 있는 객체
     * @return 페이지된 상품 목록
     */
    Slice<ItemListResponse> getItemResponses(Pageable pageable);

    /**
     * 상품 정보를 업데이트하고, 할인 ID가 있으면 해당 할인으로 변경합니다.
     *
     * @param itemUpdateRequest 업데이트할 상품의 정보
     * @param itemId 업데이트할 상품의 ID
     * @param discountId 변경할 할인 ID
     * @return 업데이트된 상품의 ID
     * @throws ItemNotFoundException 해당 ID에 대한 상품 엔티티가 없는 경우 발생
     * @throws DiscountNotFoundException 해당 ID에 대한 할인 엔티티가 없는 경우 발생
     */
    Long updateItem(ItemUpdateRequest itemUpdateRequest, Long itemId, Long discountId);

    /**
     * 상품을 삭제합니다.
     *
     * @param itemId 삭제할 상품의 ID
     */
    void deleteItem(Long itemId);
}
