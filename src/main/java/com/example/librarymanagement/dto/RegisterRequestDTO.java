package com.example.librarymanagement.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequestDTO {

    @NotBlank(message = "Ad və soyad boş ola bilməz")
    private String fullName;

    @Email(message = "Düzgün email formatı daxil edin")
    @NotBlank(message = "Email boş ola bilməz")
    private String email;

    @Size(min = 6, message = "Şifrə ən azı 6 simvol olmalıdır")
    @NotBlank(message = "Şifrə boş ola bilməz")
    private String password;
}