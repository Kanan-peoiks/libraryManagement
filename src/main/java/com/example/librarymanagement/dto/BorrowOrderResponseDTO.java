package com.example.librarymanagement.dto;

import com.example.librarymanagement.model.OrderStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class BorrowOrderResponseDTO {
    private Long orderId;
    private Long userId;
    private String userEmail;
    private LocalDateTime orderDate;
    private OrderStatus status;
    private List<BorrowItemResponseDTO> items;
}
