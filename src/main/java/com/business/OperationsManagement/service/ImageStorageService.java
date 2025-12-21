package com.business.OperationsManagement.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class ImageStorageService {

    private static final String UPLOAD_DIR = "uploads/products";

    public String store(MultipartFile file) {
        try {
            // create directory if not exists
            Path uploadPath = Paths.get(UPLOAD_DIR);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // unique filename
            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path filePath = uploadPath.resolve(fileName);

            // save file
            Files.copy(file.getInputStream(), filePath);

            // return URL/path saved in DB
            return "/uploads/products/" + fileName;

        } catch (IOException e) {
            throw new RuntimeException("Failed to store image", e);
        }
    }
}
