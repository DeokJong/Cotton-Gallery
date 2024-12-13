package com.cottongallery.backend.item.controller;

import com.cottongallery.backend.item.constants.ImageType;
import com.cottongallery.backend.item.service.ItemQueryService;
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
public class ImageController {

    private final ItemQueryService itemQueryService;

    @GetMapping("/image")
    public ResponseEntity<Resource> getFile(@RequestParam String filename, @RequestParam ImageType imageType) {

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
