package com.example.librarymanagement.service;

import com.example.librarymanagement.dto.AuthorRequestDTO;
import com.example.librarymanagement.dto.AuthorResponseDTO;
import com.example.librarymanagement.exception.NotFoundException;
import com.example.librarymanagement.model.Author;
import com.example.librarymanagement.repository.AuthorRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthorServiceTest {

    @Mock
    private AuthorRepo authorRepo;

    @InjectMocks
    private AuthorService authorService;

    private Author author;
    private AuthorRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        author = new Author();
        author.setId(1L);
        author.setName("Nihal Atsız");
        author.setBiography("Bozqurdlar");

        requestDTO = new AuthorRequestDTO();
        requestDTO.setName("Nihal Atsız");
        requestDTO.setBiography("Bozqurdlar");
    }

    @Test
    @DisplayName("Yeni yazıçı yaradıldıqdan sonra bziə ResponseDTO qaytarmalıdır.")
    void createAuthor_ShouldReturnResponseDTO() {
        when(authorRepo.save(any(Author.class))).thenReturn(author);
        AuthorResponseDTO result = authorService.createAuthor(requestDTO);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Nihal Atsız", result.getName());
        verify(authorRepo, times(1))
                .save(any(Author.class));
    }

    @Test
    @DisplayName("Uyğun ID varsa, yazıçı məlumatı uğurla qayıtmalıdır.")
    void getAuthorById_WhenAuthorExists_ShouldReturnAuthor() {
        when(authorRepo.findById(1L)).thenReturn(Optional.of(author));

        AuthorResponseDTO result = authorService.getAuthorById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Nihal Atsız", result.getName());
    }

    @Test
    @DisplayName("Uyğun ID ypxdursa, axtarış etdikdə NotFoundException atmalıdır")
    void getAuthorById_WhenAuthorDoesNotExist_ShouldThrowNotFoundException() {
        when(authorRepo.findById(99L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> authorService.getAuthorById(99L));
    }
}