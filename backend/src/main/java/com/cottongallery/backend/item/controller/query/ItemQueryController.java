package com.cottongallery.backend.item.controller.query;

import com.cottongallery.backend.common.dto.PageInfo;
import com.cottongallery.backend.common.dto.Response;
import com.cottongallery.backend.item.controller.query.api.ItemQueryApi;
import com.cottongallery.backend.item.dto.response.ItemListResponse;
import com.cottongallery.backend.item.dto.response.ItemResponse;
import com.cottongallery.backend.item.service.query.ItemQueryService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/items")
public class ItemQueryController implements ItemQueryApi {

    private final ItemQueryService itemQueryService;

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response<ItemListResponse>> retrieveItems(@RequestParam(defaultValue = "1") int page) {
        PageRequest pageRequest = PageRequest.of(page -1, 9, Sort.by(Sort.Direction.DESC, "createdDate"));

        Slice<ItemResponse> items = itemQueryService.getItemResponses(pageRequest);
        List<ItemResponse> content = items.getContent();

        ItemListResponse itemResponse = ItemListResponse.fromItemResponse(content, new PageInfo(page, items.hasNext(), items.hasPrevious()));

        return new ResponseEntity<>(Response.createResponse(HttpServletResponse.SC_OK, "상품 " + page + " 페이지 조회에 성공했습니다.", itemResponse), HttpStatus.OK);
    }
}
