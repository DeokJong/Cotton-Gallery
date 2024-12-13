package com.cottongallery.backend.item.controller;

import com.cottongallery.backend.common.dto.Response;
import com.cottongallery.backend.common.exception.InvalidRequestException;
import com.cottongallery.backend.item.constants.ImageType;
import com.cottongallery.backend.item.controller.api.ItemCommandApi;
import com.cottongallery.backend.item.dto.request.ItemCreateRequest;
import com.cottongallery.backend.item.dto.request.ItemUpdateRequest;
import com.cottongallery.backend.item.service.ItemCommandService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/items")
public class ItemCommandController implements ItemCommandApi {

    private final ItemCommandService itemCommandService;

    @Value("${file.path}")
    private String defaultFilePath;

    @Override
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Response<?>> addItem(@RequestParam(required = false) Long discountId,
                                               @RequestParam("itemImage") MultipartFile itemImage,
                                               @RequestParam("itemInfoImage") MultipartFile itemInfoImage,
                                               @ModelAttribute("itemCreateRequest") ItemCreateRequest itemCreateRequest,
                                               BindingResult bindingResult) throws IOException {

        log.info("itemCreateRequest= {}, {}, {}", itemCreateRequest.getName(), itemCreateRequest.getStockQuantity(), itemCreateRequest.getPrice());

        if (bindingResult.hasErrors()) {
            throw new InvalidRequestException("상품 생성 요청 값이 올바르지 않습니다. 다시 확인해 주세요.", bindingResult);
        }

        Long savedItemId = itemCommandService.createItem(itemCreateRequest, discountId, itemImage, itemInfoImage);

        log.info("상품 생성 요청 완료: itemId={}", savedItemId);

        return new ResponseEntity<>(Response.createResponseWithoutData(HttpServletResponse.SC_CREATED, "상품 생성에 성공했습니다."), HttpStatus.CREATED);
    }

    @Override
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

    @Override
    @PutMapping(value = "/{itemId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response<?>> editItemImage(@PathVariable Long itemId, @RequestParam MultipartFile itemImage, @RequestParam ImageType imageType) throws IOException {
        itemCommandService.updateItemImage(itemId, itemImage, imageType);

        return new ResponseEntity<>(Response.createResponseWithoutData(HttpServletResponse.SC_OK, "상품 사진 변경에 성공했습니다."), HttpStatus.OK);
    }

    @Override
    @DeleteMapping(value = "/{itemId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response<?>> removeItem(@PathVariable Long itemId) throws IOException {
        itemCommandService.deleteItem(itemId);

        log.info("상품 삭제 요청 완료: itemId={}", itemId);

        return new ResponseEntity<>(Response.createResponseWithoutData(HttpServletResponse.SC_OK, "상품 삭제에 성공했습니다."), HttpStatus.OK);
    }
}
