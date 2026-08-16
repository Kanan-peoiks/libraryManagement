package com.example.librarymanagement.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BorrowItemResponseDTO {
    private Long itemId;
    private Long bookId;
    private String bookTitle;
    private Integer daysRequested;
}
