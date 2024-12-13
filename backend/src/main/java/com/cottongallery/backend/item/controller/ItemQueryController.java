package com.cottongallery.backend.item.controller;

import com.cottongallery.backend.common.argumentResolver.annotation.Login;
import com.cottongallery.backend.common.dto.AccountSessionDTO;
import com.cottongallery.backend.common.dto.PageInfo;
import com.cottongallery.backend.common.dto.Response;
import com.cottongallery.backend.item.constants.ItemSort;
import com.cottongallery.backend.item.controller.api.ItemQueryApi;
import com.cottongallery.backend.item.dto.response.ItemDetailResponse;
import com.cottongallery.backend.item.dto.response.ItemListResponse;
import com.cottongallery.backend.item.dto.response.ItemResponse;
import com.cottongallery.backend.item.service.ItemQueryService;
import com.cottongallery.backend.item.service.LikeQueryService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/public/items")
public class ItemQueryController implements ItemQueryApi {

    private final ItemQueryService itemQueryService;
    private final LikeQueryService likeQueryService;

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response<ItemListResponse>> retrieveItems(@Login AccountSessionDTO accountSessionDTO,
                                                                    @RequestParam(defaultValue = "1") int page,
                                                                    @RequestParam(required = false) String keyword,
                                                                    @RequestParam(defaultValue = "CREATED_DATE") ItemSort itemSort) {
        PageRequest pageRequest = PageRequest.of(page -1, 9, Sort.by(Sort.Direction.DESC, itemSort.getFieldName()));

        Slice<ItemResponse> items = itemQueryService.getItemResponses(pageRequest, keyword);
        List<ItemResponse> content = items.getContent();

        if (!accountSessionDTO.getUsername().equals("anonymousUser")) {
            content.forEach(item -> {
                boolean likedByMe = likeQueryService.isLikedByAccount(accountSessionDTO, item.getItemId());
                item.setLikedByMe(likedByMe);
            });
        }

        ItemListResponse itemResponse = ItemListResponse.fromItemResponse(content, new PageInfo(page, items.hasNext(), items.hasPrevious()));

        return new ResponseEntity<>(Response.createResponse(HttpServletResponse.SC_OK, "상품 " + page + " 페이지 조회에 성공했습니다.", itemResponse), HttpStatus.OK);
    }

    @GetMapping(value = "/{itemId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response<ItemDetailResponse>> retrieveItem(@PathVariable Long itemId) {
        ItemDetailResponse itemDetailResponse = itemQueryService.getItemDetailResponse(itemId);
        return new ResponseEntity<>(Response.createResponse(HttpServletResponse.SC_OK, "상품 상세페이지 조회에 성공했습니다.", itemDetailResponse), HttpStatus.OK);
    }
}
