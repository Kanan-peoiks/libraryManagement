package com.example.librarymanagement.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class BorrowItemRequestDTO {

    @NotNull(message = "Kitab ID-si boş ola bilməz")
    private Long bookId;

    @NotNull(message = "İcarə müddəti mütləq qeyd olunmalıdır")
    @Min(value = 1, message = "İcarə müddəti ən azı 1 gün olmalıdır")
    @Max(value = 14, message = "İcazə müddəti ən çoxu 14 gün olmalıdır.")
    private Integer daysRequested;
}
