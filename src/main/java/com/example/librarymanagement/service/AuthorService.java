package com.example.librarymanagement.service;

import com.example.librarymanagement.dto.AuthorRequestDTO;
import com.example.librarymanagement.dto.AuthorResponseDTO;
import com.example.librarymanagement.exception.NotFoundException;
import com.example.librarymanagement.mapper.AuthorMapper;
import com.example.librarymanagement.model.Author;
import com.example.librarymanagement.repository.AuthorRepo;
import org.springframework.transaction.annotation.Transactional;import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepo authorRepo;
    private final AuthorMapper authorMapper;

    @Transactional
    public AuthorResponseDTO createAuthor(AuthorRequestDTO requestDTO) {
        Author author = authorMapper.toEntity(requestDTO);
        Author savedAuthor = authorRepo.save(author);
        return authorMapper.toResponseDTO(savedAuthor);
    }

    @Transactional(readOnly = true)
    public Page<AuthorResponseDTO> getAllAuthors(Pageable pageable) {
        return authorRepo.findAll(pageable)
                .map(authorMapper::toResponseDTO);
    }

    @Transactional(readOnly = true)
    public AuthorResponseDTO getAuthorById(Long id) {
        Author author = authorRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Müəllif tapılmadı. ID: " + id));
        return authorMapper.toResponseDTO(author);
    }

    @Transactional
    public void deleteAuthor(Long id) {
        if (!authorRepo.existsById(id)) {
            throw new NotFoundException("Silinəcək müəllif tapılmadı. ID: " + id);
        }
        authorRepo.deleteById(id);
    }
}
