package com.example.librarymanagement.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class MemberRequestDTO {
    @NotBlank(message = "İstifadəçinin adı boş qala bilməz.")
    private String name;
    @NotBlank(message = "Email boş qala bilməz.")
    @Email(message = "Email düzgün daxil edilməyib.")
    private String email;
}
