package com.example.librarymanagement.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Getter
@Setter
public class CreateBorrowOrderRequestDTO {

    @NotNull(message = "İstifadəçi ID-si mütləq qeyd olunmalıdır.")
    private Long userId;

    @NotEmpty(message = "Sifarişdə ən azı 1 kitab olmalıdır")
    @Valid
    private List<BorrowItemRequestDTO> items;
}