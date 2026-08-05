package com.example.librarymanagement.mapper;

import com.example.librarymanagement.dto.AuthorRequestDTO;
import com.example.librarymanagement.dto.AuthorResponseDTO;
import com.example.librarymanagement.model.Author;
import org.springframework.stereotype.Component;

@Component
public class AuthorMapper {

    public Author toEntity(AuthorRequestDTO dto) {
        Author author = new Author();
        author.setName(dto.getName());
        author.setBiography(dto.getBiography());
        return author;
    }

    public void updateEntityFromDto(AuthorRequestDTO dto, Author author) {
        author.setName(dto.getName());
        author.setBiography(dto.getBiography());
    }

    public AuthorResponseDTO toResponseDTO(Author author) {
        AuthorResponseDTO responseDTO = new AuthorResponseDTO();
        responseDTO.setId(author.getId());
        responseDTO.setName(author.getName());
        responseDTO.setBiography(author.getBiography());
        return responseDTO;
    }
}