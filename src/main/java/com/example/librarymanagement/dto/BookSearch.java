package com.example.librarymanagement.dto;

import lombok.Data;


@Data
public class BookSearch {
    private String title;
    private String genre;
    private Long authorId;
    private Long categoryId;
}