package com.example.librarymanagement.mapper;

import com.example.librarymanagement.dto.BookRequestDTO;
import com.example.librarymanagement.dto.BookResponseDTO;
import com.example.librarymanagement.model.Author;
import com.example.librarymanagement.model.Book;
import org.springframework.stereotype.Component;

@Component
public class BookMapper {

    public Book toEntity(BookRequestDTO dto, Author author) {
        Book book = new Book();
        book.setTitle(dto.getTitle());
        book.setIsbn(dto.getIsbn());
        book.setGenre(dto.getGenre());
        book.setAuthor(author);
        return book;
    }

    public void updateEntityFromDto(BookRequestDTO dto, Author author, Book book) {
        book.setTitle(dto.getTitle());
        book.setIsbn(dto.getIsbn());
        book.setGenre(dto.getGenre());
        book.setAuthor(author);
    }

    public BookResponseDTO toResponseDTO(Book book) {
        BookResponseDTO responseDTO = new BookResponseDTO();
        responseDTO.setId(book.getId());
        responseDTO.setTitle(book.getTitle());
        responseDTO.setIsbn(book.getIsbn());
        responseDTO.setGenre(book.getGenre());
        if (book.getAuthor() != null) {
            responseDTO.setAuthorId(book.getAuthor().getId());
            responseDTO.setAuthorName(book.getAuthor().getName());
        }
        return responseDTO;
    }
}