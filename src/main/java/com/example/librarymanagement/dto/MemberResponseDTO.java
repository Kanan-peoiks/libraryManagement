package com.example.librarymanagement.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;

@Data
public class MemberResponseDTO {
    private String memberId;
    private String name;
    private String email;
    private LocalDate memberBirthDate;
}
