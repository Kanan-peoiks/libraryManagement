package com.example.librarymanagement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorRequestDTO {
    @NotBlank(message = "Yazıçının adı boş qala bilmıəz.")
    @Size(min = 2, max = 50, message = "Ad 2 ilə 50 simvol arasında olmalıdır.")
    private String name;

    private String biography;
}
