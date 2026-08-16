package com.example.librarymanagement.controller;

import com.example.librarymanagement.dto.BookRequestDTO;
import com.example.librarymanagement.dto.BookResponseDTO;
import com.example.librarymanagement.dto.BookSearch;
import com.example.librarymanagement.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/books")
@RequiredArgsConstructor
@Tag(name = "Book Controller", description = "Kitabların idarə olunması üçün API-lər")
public class BookController {

    private final BookService bookService;

    @PostMapping
    @Operation(summary = "Yeni kitab əlavə etmək")
    public ResponseEntity<BookResponseDTO> createBook(@Valid @RequestBody BookRequestDTO requestDTO) {
        return new ResponseEntity<>(bookService.createBook(requestDTO), HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Bütün kitabları səhifələmə (pagination) ilə gətirmək")
    public ResponseEntity<Page<BookResponseDTO>> getAllBooks(Pageable pageable) {
        return ResponseEntity.ok(bookService.getAllBooks(pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "ID-yə görə kitabı gətirmək")
    public ResponseEntity<BookResponseDTO> getBookById(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.getBookById(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "ID-yə görə kitab məlumatlarını yeniləmək")
    public ResponseEntity<BookResponseDTO> updateBook(@PathVariable Long id, @Valid @RequestBody BookRequestDTO requestDTO) {
        return ResponseEntity.ok(bookService.updateBook(id, requestDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "ID-yə görə kitabı silmək")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    @Operation(summary = "Kitabların dinamik axtarışı və sorğusu")
    public ResponseEntity<Page<BookResponseDTO>> searchBooks(
            BookSearch search,
            @PageableDefault(size = 5, sort = "title") Pageable pageable
    ) {
        return ResponseEntity.ok(bookService.searchBooks(search, pageable));
    }
}