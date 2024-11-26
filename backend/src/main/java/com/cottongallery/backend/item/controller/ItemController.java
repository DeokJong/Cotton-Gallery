package com.cottongallery.backend.item.controller;

import com.cottongallery.backend.common.dto.ListResponse;
import com.cottongallery.backend.common.dto.PageInfo;
import com.cottongallery.backend.common.dto.Response;
import com.cottongallery.backend.common.exception.InvalidRequestException;
import com.cottongallery.backend.item.controller.api.ItemApi;
import com.cottongallery.backend.item.dto.request.ItemCreateRequest;
import com.cottongallery.backend.item.dto.request.ItemUpdateRequest;
import com.cottongallery.backend.item.dto.response.ItemListResponse;
import com.cottongallery.backend.item.service.command.ItemCommandService;
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
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/items")
public class ItemController implements ItemApi {

    private final ItemQueryService itemQueryService;
    private final ItemCommandService itemCommandService;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response<?>> addItem(@RequestParam(required = false) Long discountId,
                                               @Validated @RequestBody ItemCreateRequest itemCreateRequest,
                                               BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new InvalidRequestException("상품 생성 요청 값이 올바르지 않습니다. 다시 확인해 주세요.", bindingResult);
        }

        Long savedItemId = itemCommandService.createItem(itemCreateRequest, discountId);

        log.info("상품 생성 요청 완료: itemId={}", savedItemId);

        return new ResponseEntity<>(Response.createResponseWithoutData(HttpServletResponse.SC_CREATED, "상품 생성에 성공했습니다."), HttpStatus.CREATED);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response<ListResponse<List<ItemListResponse>>>> retrieveItems(@RequestParam(defaultValue = "1") int page) {
        PageRequest pageRequest = PageRequest.of(page -1, 10, Sort.by(Sort.Direction.DESC, "createdBy"));

        Slice<ItemListResponse> items = itemQueryService.getItemResponses(pageRequest);
        List<ItemListResponse> content = items.getContent();

        ListResponse<List<ItemListResponse>> itemResponse = new ListResponse<>(new PageInfo(page, items.hasNext(), items.hasPrevious()), content);

        return new ResponseEntity<>(Response.createResponse(HttpServletResponse.SC_OK, "상품 " + page + " 페이지 조회에 성공했습니다.", itemResponse), HttpStatus.OK);
    }

    @PatchMapping(value = "/{itemId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response<?>> editItem(@PathVariable Long itemId,
                                                @RequestParam(required = false) Long discountId,
                                                @Validated @RequestBody ItemUpdateRequest itemUpdateRequest,
                                                BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InvalidRequestException("상품 수정 요청 값이 올바르지 않습니다. 다시 확인해 주세요.", bindingResult);
        }

        Long savedItemId = itemCommandService.updateItem(itemUpdateRequest, itemId, discountId);

        log.info("상품 수정 요청 완료: itemId={}", savedItemId);

        return new ResponseEntity<>(Response.createResponseWithoutData(HttpServletResponse.SC_OK, "상품 수정에 성공했습니다."), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{itemId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response<?>> removeItem(@PathVariable Long itemId) {
        itemCommandService.deleteItem(itemId);

        log.info("상품 삭제 요청 완료: itemId={}", itemId);

        return new ResponseEntity<>(Response.createResponseWithoutData(HttpServletResponse.SC_OK, "상품 삭제에 성공했습니다."), HttpStatus.OK);
    }
}
