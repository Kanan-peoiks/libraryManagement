package com.example.librarymanagement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookRequestDTO {
    @NotBlank(message = "Kitabın adı boş qala bilməz.")
    private String title;

    @NotBlank(message = "ISBN boş qala bilməz.")
    private String isbn;

    private String genre;

    @NotNull(message = "Yazıçı ID-si boş qala bilməz.")
    private Long authorId;

}