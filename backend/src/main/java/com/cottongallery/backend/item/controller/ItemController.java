package com.cottongallery.backend.item.controller;

import com.cottongallery.backend.common.dto.ListResponse;
import com.cottongallery.backend.common.dto.PageInfo;
import com.cottongallery.backend.common.dto.Response;
import com.cottongallery.backend.common.exception.InvalidRequestException;
import com.cottongallery.backend.item.dto.request.ItemCreateRequest;
import com.cottongallery.backend.item.dto.request.ItemUpdateRequest;
import com.cottongallery.backend.item.dto.response.ItemListResponse;
import com.cottongallery.backend.item.service.ItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "상품 관리", description = "상품 관련 API")
public class ItemController {

    private final ItemService itemService;

    @Operation(summary = "상품 등록", description = "새로운 상품을 등록합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "상품 등록 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 상품 등록 요청")
    })
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response<?>> addItem(@Parameter(description = "적용할 할인 ID. 할인 미적용 시 생략 가능") @RequestParam(required = false) Long discountId,
                                               @Validated @RequestBody ItemCreateRequest itemCreateRequest,
                                               BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new InvalidRequestException("상품 생성 요청 값이 올바르지 않습니다. 다시 확인해 주세요.", bindingResult);
        }

        Long savedItemId = itemService.createItem(itemCreateRequest, discountId);

        log.info("상품 생성 요청 완료: itemId={}", savedItemId);

        return new ResponseEntity<>(Response.createResponseWithoutData(HttpServletResponse.SC_CREATED, "상품 생성에 성공했습니다."), HttpStatus.CREATED);
    }

    @Operation(summary = "상품 목록 조회", description = "특정 페이지의 상품 목록을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "특정 페이지 상품 목록 조회 성공")
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response<ListResponse<List<ItemListResponse>>>> retrieveItems(@Parameter(description = "조회할 페이지 번호") @RequestParam(defaultValue = "1") int page) {
        PageRequest pageRequest = PageRequest.of(page -1, 10, Sort.by(Sort.Direction.DESC, "createdBy"));

        Slice<ItemListResponse> items = itemService.getItemResponses(pageRequest);
        List<ItemListResponse> content = items.getContent();

        ListResponse<List<ItemListResponse>> itemResponse = new ListResponse<>(new PageInfo(page, items.hasNext(), items.hasPrevious()), content);

        return new ResponseEntity<>(Response.createResponse(HttpServletResponse.SC_OK, "상품 " + page + " 페이지 조회에 성공했습니다.", itemResponse), HttpStatus.OK);
    }

    @Operation(summary = "상품 수정", description = "특정 상품을 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "상품 수정 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 상품 수정 요청")
    })
    @PatchMapping(value = "/{itemId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response<?>> editItem(@Parameter(description = "수정할 상품 ID") @PathVariable Long itemId,
                                                @Parameter(description = "변경할 할인 ID, 할인 미적용 시 생략 가능") @RequestParam(required = false) Long discountId,
                                                @Validated @RequestBody ItemUpdateRequest itemUpdateRequest,
                                                BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InvalidRequestException("상품 수정 요청 값이 올바르지 않습니다. 다시 확인해 주세요.", bindingResult);
        }

        Long savedItemId = itemService.updateItem(itemUpdateRequest, itemId, discountId);

        log.info("상품 수정 요청 완료: itemId={}", savedItemId);

        return new ResponseEntity<>(Response.createResponseWithoutData(HttpServletResponse.SC_OK, "상품 수정에 성공했습니다."), HttpStatus.OK);
    }

    @Operation(summary = "상품 삭제", description = "특정 상품을 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "상품 삭제 성공"),
    })
    @DeleteMapping(value = "/{itemId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response<?>> removeItem(@Parameter(description = "삭제할 상품 ID") @PathVariable Long itemId) {
        itemService.deleteItem(itemId);

        log.info("상품 삭제 요청 완료: itemId={}", itemId);

        return new ResponseEntity<>(Response.createResponseWithoutData(HttpServletResponse.SC_OK, "상품 삭제에 성공했습니다."), HttpStatus.OK);
    }
}
