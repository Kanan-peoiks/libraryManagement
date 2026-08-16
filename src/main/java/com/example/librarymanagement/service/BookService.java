package com.example.librarymanagement.service;

import com.example.librarymanagement.dto.BookRequestDTO;
import com.example.librarymanagement.dto.BookResponseDTO;
import com.example.librarymanagement.dto.BookSearch;
import com.example.librarymanagement.exception.NotFoundException;
import com.example.librarymanagement.mapper.BookMapper;
import com.example.librarymanagement.model.Author;
import com.example.librarymanagement.model.Book;
import com.example.librarymanagement.repository.AuthorRepo;
import com.example.librarymanagement.repository.BookRepe;
import com.example.librarymanagement.specification.BookSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepe bookRepository;
    private final AuthorRepo authorRepo;
    private final BookMapper bookMapper;

    @Transactional
    public BookResponseDTO createBook(BookRequestDTO requestDTO) {
        Author author = authorRepo.findById(requestDTO.getAuthorId())
                .orElseThrow(() -> new NotFoundException("Müəllif tapılmadı. ID: " + requestDTO.getAuthorId()));

        Book book = bookMapper.toEntity(requestDTO, author);
        Book savedBook = bookRepository.save(book);

        return bookMapper.toResponseDTO(savedBook);
    }

    @Transactional(readOnly = true)
    public Page<BookResponseDTO> getAllBooks(Pageable pageable) {
        return bookRepository.findAll(pageable)
                .map(bookMapper::toResponseDTO);
    }

    @Transactional(readOnly = true)
    public BookResponseDTO getBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Kitab tapılmadı. ID: " + id));
        return bookMapper.toResponseDTO(book);
    }

    @Transactional
    public BookResponseDTO updateBook(Long id, BookRequestDTO requestDTO) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Yenilənəcək kitab tapılmadı. ID: " + id));

        Author author = authorRepo.findById(requestDTO.getAuthorId())
                .orElseThrow(() -> new NotFoundException("Müəllif tapılmadı. ID: " + requestDTO.getAuthorId()));

        bookMapper.updateEntityFromDto(requestDTO, author, book);
        Book updatedBook = bookRepository.save(book);
        return bookMapper.toResponseDTO(updatedBook);
    }

    @Transactional
    public void deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new NotFoundException("Silinəcək kitab tapılmadı. ID: " + id);
        }
        bookRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Page<BookResponseDTO> searchBooks(BookSearch search, Pageable pageable) {
        Specification<Book> spec = BookSpecification.filterBooks(search);
        return bookRepository.findAll(spec, pageable)
                .map(bookMapper::toResponseDTO);
    }
}