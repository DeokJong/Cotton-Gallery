package com.cottongallery.backend.item.controller.api;

import com.cottongallery.backend.common.dto.ListResponse;
import com.cottongallery.backend.common.dto.Response;
import com.cottongallery.backend.item.dto.request.ItemCreateRequest;
import com.cottongallery.backend.item.dto.request.ItemUpdateRequest;
import com.cottongallery.backend.item.dto.response.ItemListResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "상품 관리", description = "상품 관련 API")
public interface ItemApi {

    @Operation(summary = "상품 등록", description = "새로운 상품을 등록합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "상품 등록 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 상품 등록 요청")
    })
    ResponseEntity<Response<?>> addItem(@Parameter(description = "적용할 할인 ID. 할인 미적용 시 생략 가능") @RequestParam(required = false) Long discountId,
                                        @Validated @RequestBody ItemCreateRequest itemCreateRequest,
                                        BindingResult bindingResult);


    @Operation(summary = "상품 목록 조회", description = "특정 페이지의 상품 목록을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "특정 페이지 상품 목록 조회 성공")
    })
    ResponseEntity<Response<ListResponse<List<ItemListResponse>>>> retrieveItems(@Parameter(description = "조회할 페이지 번호") @RequestParam(defaultValue = "1") int page);


    @Operation(summary = "상품 수정", description = "특정 상품을 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "상품 수정 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 상품 수정 요청")
    })
    public ResponseEntity<Response<?>> editItem(@Parameter(description = "수정할 상품 ID") @PathVariable Long itemId,
                                                @Parameter(description = "변경할 할인 ID, 할인 미적용 시 생략 가능") @RequestParam(required = false) Long discountId,
                                                @Validated @RequestBody ItemUpdateRequest itemUpdateRequest,
                                                BindingResult bindingResult);


    @Operation(summary = "상품 삭제", description = "특정 상품을 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "상품 삭제 성공"),
    })
    ResponseEntity<Response<?>> removeItem(@Parameter(description = "삭제할 상품 ID") @PathVariable Long itemId);
}
