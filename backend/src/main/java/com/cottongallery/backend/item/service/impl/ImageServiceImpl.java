package com.cottongallery.backend.item.service.impl;

import com.cottongallery.backend.item.service.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    @Value("${file.path}")
    private String defaultFilePath;

    @Override
    public void deleteImage(String imagePath) throws IOException {
        // 파일 객체 생성
        File file = new File(imagePath);

        // 파일 존재 여부 확인
        if (!file.exists() || !file.isFile()) {
            throw new IOException("파일이 존재하지 않거나 유효한 파일이 아닙니다.");
        }

        // 파일 삭제
        if (file.delete()) {
            log.info("파일 삭제 성공");
        } else {
            throw new IOException("파일 삭제 실패");
        }
    }

    @Override
    public String saveFile(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            return "";
        }
        String fullPath = defaultFilePath + UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        log.info("파일 저장 fullPath={}", fullPath);
        file.transferTo(new File(fullPath));
        return fullPath;
    }
}
