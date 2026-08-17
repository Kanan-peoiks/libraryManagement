package com.example.librarymanagement.service;

import com.example.librarymanagement.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class FileStorageService {

    private final Path uploadDir = Paths.get("uploads/covers");


    private static final List<String> ALLOWED_CONTENT_TYPES = Arrays.asList("image/jpeg", "image/png", "image/webp");
    private static final List<String> ALLOWED_EXTENSIONS = Arrays.asList(".jpg", ".jpeg", ".png", ".webp");
    private static final long MAX_FILE_SIZE = 2 * 1024 * 1024; // 2MB

    public FileStorageService() {
        try {
            Files.createDirectories(uploadDir);
        } catch (IOException e) {
            throw new RuntimeException("Fayl qovluğu yaradıla bilmədi", e);
        }
    }

    public String saveFile(MultipartFile file) {

        if (file.isEmpty() || file.getSize() > MAX_FILE_SIZE) {
            throw new IllegalArgumentException("Fayl boş ola bilməz və maksimum 2MB ola bilər.");
        }


        String contentType = file.getContentType();
        if (contentType == null || !ALLOWED_CONTENT_TYPES.contains(contentType.toLowerCase())) {
            throw new IllegalArgumentException("Yalnız JPEG, PNG və WEBP formatında şəkillər qəbul edilir.");
        }


        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null || !hasAllowedExtension(originalFilename)) {
            throw new IllegalArgumentException("İcazə verilməyən fayl uzantısı!");
        }

        // 4. Unikal fayl adı yaradıb diskə yazırıq
        try {
            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String fileName = UUID.randomUUID() + extension;
            Path filePath = uploadDir.resolve(fileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            return fileName;
        } catch (IOException e) {
            throw new RuntimeException("Fayl saxlanılarkən xəta baş verdi", e);
        }
    }

    public byte[] loadFile(String fileName) {
        try {
            Path filePath = uploadDir.resolve(fileName);
            if (!Files.exists(filePath)) {
                throw new NotFoundException("Fayl tapılmadı: " + fileName);
            }
            return Files.readAllBytes(filePath);
        } catch (IOException e) {
            throw new RuntimeException("Fayl oxunarkən xəta baş verdi", e);
        }
    }

    private boolean hasAllowedExtension(String filename) {
        String lowerCaseName = filename.toLowerCase();
        return ALLOWED_EXTENSIONS.stream().anyMatch(lowerCaseName::endsWith);
    }
}