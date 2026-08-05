package com.example.librarymanagement.controller;

import com.example.librarymanagement.dto.AuthorRequestDTO;
import com.example.librarymanagement.dto.AuthorResponseDTO;
import com.example.librarymanagement.service.AuthorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/authors")
@RequiredArgsConstructor
@Tag(name = "Author Controller", description = "Müəlliflərin idarə olunması üçün API-lər")
public class AuthorController {

    private final AuthorService authorService;

    @PostMapping
    @Operation(summary = "Yeni müəllif əlavə etmək")
    public ResponseEntity<AuthorResponseDTO> createAuthor(@Valid @RequestBody AuthorRequestDTO requestDTO) {
        return new ResponseEntity<>(authorService.createAuthor(requestDTO), HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Bütün müəllifləri səhifələmə (pagination) ilə gətirmək")
    public ResponseEntity<Page<AuthorResponseDTO>> getAllAuthors(Pageable pageable) {
        return ResponseEntity.ok(authorService.getAllAuthors(pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "ID-yə görə müəllifi gətirmək")
    public ResponseEntity<AuthorResponseDTO> getAuthorById(@PathVariable Long id) {
        return ResponseEntity.ok(authorService.getAuthorById(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "ID-yə görə müəllif məlumatlarını yeniləmək")
    public ResponseEntity<AuthorResponseDTO> updateAuthor(@PathVariable Long id, @Valid @RequestBody AuthorRequestDTO requestDTO) {
        return ResponseEntity.ok(authorService.updateAuthor(id, requestDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "ID-yə görə müəllifi silmək")
    public ResponseEntity<Void> deleteAuthor(@PathVariable Long id) {
        authorService.deleteAuthor(id);
        return ResponseEntity.noContent().build();
    }
}