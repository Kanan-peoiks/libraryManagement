package com.example.librarymanagement.service;

import com.example.librarymanagement.dto.BookRequestDTO;
import com.example.librarymanagement.dto.BookResponseDTO;
import com.example.librarymanagement.exception.NotFoundException;
import com.example.librarymanagement.model.Author;
import com.example.librarymanagement.model.Book;
import com.example.librarymanagement.repository.AuthorRepo;
import com.example.librarymanagement.repository.BookRepe;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepe bookRepository;
    private final AuthorRepo authorRepository;

    public BookResponseDTO createBook(BookRequestDTO requestDTO) {
        Author author = authorRepository.findById(requestDTO.getAuthorId())
                .orElseThrow(() -> new NotFoundException("Müəllif tapılmadı. ID: " + requestDTO.getAuthorId()));

        Book newBook = new Book();
        newBook.setTitle(requestDTO.getTitle());
        newBook.setIsbn(requestDTO.getIsbn());
        newBook.setGenre(requestDTO.getGenre());
        newBook.setAuthor(author);

        Book savedBook = bookRepository.save(newBook);

        BookResponseDTO responseDTO = new BookResponseDTO();
        responseDTO.setId(savedBook.getId());
        responseDTO.setTitle(savedBook.getTitle());
        responseDTO.setIsbn(savedBook.getIsbn());
        responseDTO.setGenre(savedBook.getGenre());

        responseDTO.setAuthorId(savedBook.getAuthor().getId());
        responseDTO.setAuthorName(savedBook.getAuthor().getName());

        return responseDTO;
    }

    public BookResponseDTO getBookById(Long id) {

        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Kitab tapılmadı. ID: " + id));

        BookResponseDTO responseDTO = new BookResponseDTO();
        responseDTO.setId(book.getId());
        responseDTO.setTitle(book.getTitle());
        responseDTO.setIsbn(book.getIsbn());
        responseDTO.setGenre(book.getGenre());
        responseDTO.setAuthorId(book.getAuthor().getId());
        responseDTO.setAuthorName(book.getAuthor().getName());

        return responseDTO;
    }

    //səhifələmə
    public Page<BookResponseDTO> getAllBooks(Pageable pageable) {

        Page<Book> booksPage = bookRepository.findAll(pageable);

        return booksPage.map(book -> {
            BookResponseDTO responseDTO = new BookResponseDTO();
            responseDTO.setId(book.getId());
            responseDTO.setTitle(book.getTitle());
            responseDTO.setIsbn(book.getIsbn());
            responseDTO.setGenre(book.getGenre());
            responseDTO.setAuthorId(book.getAuthor().getId());
            responseDTO.setAuthorName(book.getAuthor().getName());
            return responseDTO;
        });
    }

    public BookResponseDTO updateBook(Long id, BookRequestDTO requestDTO) {

        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Kitab tapılmadı. ID: " + id));

        Author authorFromDb = authorRepository.findById(requestDTO.getAuthorId())
                .orElseThrow(() -> new RuntimeException("Müəllif tapılmadı. ID: " + requestDTO.getAuthorId()));

        existingBook.setTitle(requestDTO.getTitle());
        existingBook.setIsbn(requestDTO.getIsbn());
        existingBook.setGenre(requestDTO.getGenre());
        existingBook.setAuthor(authorFromDb);

        Book updatedBook = bookRepository.save(existingBook);

        BookResponseDTO responseDTO = new BookResponseDTO();
        responseDTO.setId(updatedBook.getId());
        responseDTO.setTitle(updatedBook.getTitle());
        responseDTO.setIsbn(updatedBook.getIsbn());
        responseDTO.setGenre(updatedBook.getGenre());
        responseDTO.setAuthorId(updatedBook.getAuthor().getId());
        responseDTO.setAuthorName(updatedBook.getAuthor().getName());

        return responseDTO;
    }

    public void deleteBook(Long id) {
        if (!bookRepository.existsById(id)) { //kitab yoxdu
            throw new RuntimeException("Kitab tapılmadı. ID: " + id);
        }
        bookRepository.deleteById(id);
    }
}