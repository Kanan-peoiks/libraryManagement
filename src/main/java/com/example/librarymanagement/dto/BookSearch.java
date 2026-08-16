package com.example.librarymanagement.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Data
@Getter
@Setter
public class BookSearch {
    private String title;
    private String genre;
    private Long authorId;
    private Long categoryId;
}