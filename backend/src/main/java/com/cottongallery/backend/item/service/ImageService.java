package com.cottongallery.backend.item.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageService {
    void deleteImage(String imagePath) throws IOException;

    String saveFile(MultipartFile file) throws IOException;
}
