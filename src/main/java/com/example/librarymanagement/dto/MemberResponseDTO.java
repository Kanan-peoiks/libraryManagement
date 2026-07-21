package com.example.librarymanagement.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberResponseDTO {
    private Long memberId;
    private String name;
    private String email;
    private LocalDate memberBirthDate;
}
