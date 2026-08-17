package com.example.librarymanagement.controller;

import com.example.librarymanagement.dto.BookRequestDTO;
import com.example.librarymanagement.dto.BookResponseDTO;
import com.example.librarymanagement.dto.BookSearch;
import com.example.librarymanagement.service.BookService;
import com.example.librarymanagement.service.FileStorageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/books")
@RequiredArgsConstructor
@Tag(name = "Book Controller", description = "Kitabların idarə olunması və fayl əməliyyatları üçün API-lər")
public class BookController {

    private final BookService bookService;
    private final FileStorageService fileStorageService;

    @PostMapping
    @Operation(summary = "Yeni kitab əlavə etmək", description = "Sistemə yeni kitab məlumatları daxil edir.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Kitab uğurla yaradıldı"),
            @ApiResponse(responseCode = "400", description = "Daxil edilən məlumatlar yanlışdır"),
            @ApiResponse(responseCode = "401", description = "Auth token tapılmadı və ya keçərsizdir")
    })
    public ResponseEntity<BookResponseDTO> createBook(@Valid @RequestBody BookRequestDTO requestDTO) {
        return new ResponseEntity<>(bookService.createBook(requestDTO), HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Bütün kitabları səhifələmə ilə gətirmək", description = "Mövcud bütün kitabları pageable vasitəsilə siyahılayır.")
    @ApiResponse(responseCode = "200", description = "Uğurlu əməliyyat")
    public ResponseEntity<Page<BookResponseDTO>> getAllBooks(Pageable pageable) {
        return ResponseEntity.ok(bookService.getAllBooks(pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "ID-yə görə kitabı gətirmək", description = "Verilmiş ID-yə uyğun kitab məlumatlarını qaytarır (Keşdən oxunur).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Kitab tapıldı"),
            @ApiResponse(responseCode = "404", description = "Kitab tapılmadı")
    })
    public ResponseEntity<BookResponseDTO> getBookById(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.getBookById(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "ID-yə görə kitab məlumatlarını yeniləmək")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Kitab yeniləndi"),
            @ApiResponse(responseCode = "400", description = "Yanlış sorğu məlumatı"),
            @ApiResponse(responseCode = "404", description = "Kitab tapılmadı")
    })
    public ResponseEntity<BookResponseDTO> updateBook(@PathVariable Long id, @Valid @RequestBody BookRequestDTO requestDTO) {
        return ResponseEntity.ok(bookService.updateBook(id, requestDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "ID-yə görə kitabı silmək")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Kitab uğurla silindi"),
            @ApiResponse(responseCode = "404", description = "Kitab tapılmadı")
    })
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    @Operation(summary = "Kitabların dinamik axtarışı və sorğusu")
    @ApiResponse(responseCode = "200", description = "Axtarış nəticələri")
    public ResponseEntity<Page<BookResponseDTO>> searchBooks(
            BookSearch search,
            @PageableDefault(size = 5, sort = "title") Pageable pageable
    ) {
        return ResponseEntity.ok(bookService.searchBooks(search, pageable));
    }

    @PostMapping(value = "/{id}/cover", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Kitaba üzlük şəkli yükləmək", description = "JPEG, PNG və ya WEBP formatında maksimum 2MB şəkil yükləyir.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Şəkil uğurla yükləndi"),
            @ApiResponse(responseCode = "400", description = "Yanlış fayl formatı və ya ölçü limiti keçilib"),
            @ApiResponse(responseCode = "404", description = "Kitab tapılmadı")
    })
    public ResponseEntity<String> uploadCover(
            @PathVariable Long id,
            @Parameter(
                    description = "Yüklənəcək şəkil faylı",
                    required = true,
                    content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE,
                            schema = @Schema(type = "string", format = "binary"))
            )
            @RequestParam("file") MultipartFile file) {

        String savedFileName = fileStorageService.saveFile(file);
        return ResponseEntity.ok("Şəkil uğurla yükləndi. Fayl adı: " + savedFileName);
    }

    @GetMapping("/{id}/cover/{fileName}")
    @Operation(summary = "Kitabın üzlük şəklini endirmək / baxmaq")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Şəkil tapıldı"),
            @ApiResponse(responseCode = "404", description = "Fayl tapılmadı")
    })
    public ResponseEntity<byte[]> downloadCover(@PathVariable Long id, @PathVariable String fileName) {
        byte[] imageBytes = fileStorageService.loadFile(fileName);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);

        return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
    }
}