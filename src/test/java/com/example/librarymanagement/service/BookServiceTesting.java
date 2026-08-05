package com.example.librarymanagement.service;

import com.example.librarymanagement.dto.BookRequestDTO;
import com.example.librarymanagement.dto.BookResponseDTO;
import com.example.librarymanagement.exception.NotFoundException;
import com.example.librarymanagement.mapper.BookMapper;
import com.example.librarymanagement.model.Author;
import com.example.librarymanagement.model.Book;
import com.example.librarymanagement.repository.AuthorRepo;
import com.example.librarymanagement.repository.BookRepe;
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
class BookServiceTest {

    @Mock
    private BookRepe bookRepository;

    @Mock
    private AuthorRepo authorRepo;

    @Mock
    private BookMapper bookMapper;

    @InjectMocks
    private BookService bookService;

    private Book book;
    private Author author;
    private BookRequestDTO requestDTO;
    private BookResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        author = new Author();
        author.setId(1L);
        author.setName("Nizami Gəncəvi");

        book = new Book();
        book.setId(10L);
        book.setTitle("Xosrov və Şirin");
        book.setIsbn("978-1234567890");
        book.setGenre("Poema");
        book.setAuthor(author);

        requestDTO = new BookRequestDTO();
        requestDTO.setTitle("Xosrov və Şirin");
        requestDTO.setIsbn("978-1234567890");
        requestDTO.setGenre("Poema");
        requestDTO.setAuthorId(1L);

        responseDTO = new BookResponseDTO();
        responseDTO.setId(10L);
        responseDTO.setTitle("Xosrov və Şirin");
        responseDTO.setAuthorName("Nizami Gəncəvi");
    }

    @Test
    @DisplayName("Uğurla yeni kitab yaradılmalıdır")
    void createBook_Success() {
        when(authorRepo.findById(1L)).thenReturn(Optional.of(author));
        when(bookMapper.toEntity(requestDTO, author)).thenReturn(book);
        when(bookRepository.save(any(Book.class))).thenReturn(book);
        when(bookMapper.toResponseDTO(book)).thenReturn(responseDTO);

        BookResponseDTO result = bookService.createBook(requestDTO);

        assertNotNull(result);
        assertEquals("Xosrov və Şirin", result.getTitle());
        verify(authorRepo, times(1)).findById(1L);
        verify(bookRepository, times(1)).save(book);
    }

    @Test
    @DisplayName("Müəllif tapılmadıqda NotFoundException atmalıdır")
    void createBook_AuthorNotFound_ThrowsException() {
        when(authorRepo.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> bookService.createBook(requestDTO));
        verify(bookRepository, never()).save(any());
    }

    @Test
    @DisplayName("Mövcud ID üzrə kitabı uğurla gətirməlidir")
    void getBookById_Success() {
        when(bookRepository.findById(10L)).thenReturn(Optional.of(book));
        when(bookMapper.toResponseDTO(book)).thenReturn(responseDTO);

        BookResponseDTO result = bookService.getBookById(10L);

        assertNotNull(result);
        assertEquals(10L, result.getId());
        verify(bookRepository, times(1)).findById(10L);
    }

    @Test
    @DisplayName("Kitab tapılmadıqda NotFoundException atmalıdır")
    void getBookById_NotFound_ThrowsException() {
        when(bookRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> bookService.getBookById(99L));
    }

    @Test
    @DisplayName("Kitab məlumatları uğurla yenilənməlidir")
    void updateBook_Success() {
        when(bookRepository.findById(10L)).thenReturn(Optional.of(book));
        when(authorRepo.findById(1L)).thenReturn(Optional.of(author));
        doNothing().when(bookMapper).updateEntityFromDto(requestDTO, author, book);
        when(bookRepository.save(book)).thenReturn(book);
        when(bookMapper.toResponseDTO(book)).thenReturn(responseDTO);

        BookResponseDTO updatedResult = bookService.updateBook(10L, requestDTO);

        assertNotNull(updatedResult);
        verify(bookRepository, times(1)).save(book);
    }

    @Test
    @DisplayName("Kitab silinməsi uğurla icra edilməlidir")
    void deleteBook_Success() {
        when(bookRepository.existsById(10L)).thenReturn(true);
        doNothing().when(bookRepository).deleteById(10L);

        assertDoesNotThrow(() -> bookService.deleteBook(10L));
        verify(bookRepository, times(1)).deleteById(10L);
    }

    @Test
    @DisplayName("Silinəcək kitab tapılmadıqda NotFoundException atmalıdır")
    void deleteBook_NotFound_ThrowsException() {
        when(bookRepository.existsById(99L)).thenReturn(false);

        assertThrows(NotFoundException.class, () -> bookService.deleteBook(99L));
        verify(bookRepository, never()).deleteById(anyLong());
    }
}