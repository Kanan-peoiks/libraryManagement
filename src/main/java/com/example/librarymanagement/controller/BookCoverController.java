package com.example.librarymanagement.controller;

import com.example.librarymanagement.service.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookCoverController {

    private final FileStorageService fileStorageService;

    //yükləmə
    @PostMapping(value = "/{id}/cover", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadCover(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        String savedFileName = fileStorageService.saveFile(file);
        return ResponseEntity.ok("Şəkil uğurla yükləndi. Fayl adı: " + savedFileName);
    }


    @GetMapping("/{id}/cover/{fileName}")
    public ResponseEntity<byte[]> downloadCover(@PathVariable Long id, @PathVariable String fileName) {
        byte[] imageBytes = fileStorageService.loadFile(fileName);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);

        return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
    }
}