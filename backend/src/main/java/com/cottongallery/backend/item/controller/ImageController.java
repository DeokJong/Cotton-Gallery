package com.cottongallery.backend.item.controller;

import com.cottongallery.backend.item.constants.ImageType;
import com.cottongallery.backend.item.service.ItemQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.nio.file.Files;

@Slf4j
@RestController
@RequestMapping("/api/public")
@RequiredArgsConstructor
@Tag(name = "이미지 관리", description = "이미지 관련 API")
public class ImageController {

    private final ItemQueryService itemQueryService;

    @Operation(summary = "이미지 조회", description = "특정 이미지를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "특정 이미지 조회 성공")
    })
    @GetMapping("/image")
    public ResponseEntity<Resource> getFile(@RequestParam String filename,
                                            @Parameter(description = "조회할 이미지의 타입. 가능한 값: `ITEM_IMAGE` (상품 사진), `ITEM_INFO_IMAGE` (상품 정보 사진)")
                                            @RequestParam ImageType imageType) {

        boolean isValidItem = itemQueryService.isItemRelatedToImage(filename, imageType);

        if (!isValidItem) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null); // 파일 접근 권한 없음
        }

        try {
            // 파일 경로 설정
            File file = new File(filename);

            if (!file.exists()) {
                return ResponseEntity.notFound().build();
            }

            // 파일을 Resource로 변환
            Resource resource = new UrlResource(file.toURI());

            // Content-Type 설정
            String contentType = Files.probeContentType(file.toPath());
            if (contentType == null || !contentType.startsWith("image/")) {
                log.warn("이미지 파일이 아님: {}", filename);
                return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).build();
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .body(resource);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
