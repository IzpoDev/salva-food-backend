package com.salvafood.api.salvafood_api.service;

import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

public interface ImageStorageService {
    String saveImage(MultipartFile file, Long productId) throws IOException;
    void deleteImage(String imageUrl) throws IOException;
}
