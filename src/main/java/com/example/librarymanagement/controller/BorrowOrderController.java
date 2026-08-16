package com.example.librarymanagement.controller;

import com.example.librarymanagement.dto.BorrowOrderResponseDTO;
import com.example.librarymanagement.dto.CreateBorrowOrderRequestDTO;
import com.example.librarymanagement.service.BorrowOrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/borrow-orders")
@RequiredArgsConstructor
@Tag(name = "Borrow Order Controller", description = "Kitab icarə sifarişlərinin idarə olunması API-ləri")
public class BorrowOrderController {

    private final BorrowOrderService borrowOrderService;

    @PostMapping
    @Operation(summary = "Yeni kitab icarə sifarişi yaratmaq")
    public ResponseEntity<BorrowOrderResponseDTO> createBorrowOrder(
            @Valid @RequestBody CreateBorrowOrderRequestDTO request) {
        return new ResponseEntity<>(borrowOrderService.createBorrowOrder(request), HttpStatus.CREATED);
    }
}