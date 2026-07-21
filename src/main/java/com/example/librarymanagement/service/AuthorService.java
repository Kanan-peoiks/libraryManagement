package com.example.librarymanagement.service;

import com.example.librarymanagement.dto.AuthorRequestDTO;
import com.example.librarymanagement.dto.AuthorResponseDTO;
import com.example.librarymanagement.model.Author;
import com.example.librarymanagement.repository.AuthorRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthorService {
    private final AuthorRepo authorRepo;

    public AuthorResponseDTO createAuthor(AuthorRequestDTO requestDTO) {
        Author newAuthor = new Author();
        newAuthor.setName(requestDTO.getName());
        newAuthor.setBiography(requestDTO.getBiography());

        Author savedAuthor = authorRepo.save(newAuthor);

        AuthorResponseDTO responseDTO = new AuthorResponseDTO();
        responseDTO.setId(savedAuthor.getId());
        responseDTO.setName(savedAuthor.getName());
        responseDTO.setBiography(savedAuthor.getBiography());

        return responseDTO;
    }

    public AuthorResponseDTO getAuthorById(Long id) {
        Author author = authorRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Müəllif tapılmadı. ID: " + id));

        AuthorResponseDTO responseDTO = new AuthorResponseDTO();
        responseDTO.setId(author.getId());
        responseDTO.setName(author.getName());
        responseDTO.setBiography(author.getBiography());

        return responseDTO;
    }

    //səhifələmə məntiqi
    public Page<AuthorResponseDTO> getAllAuthors(Pageable pageable) {

        Page<Author> authorsPage = authorRepo.findAll(pageable);

        return authorsPage.map(author -> {
            AuthorResponseDTO responseDTO = new AuthorResponseDTO();
            responseDTO.setId(author.getId());
            responseDTO.setName(author.getName());
            responseDTO.setBiography(author.getBiography());
            return responseDTO;
        });
    }

    public AuthorResponseDTO updateAuthor(Long id, AuthorRequestDTO requestDTO) {
        Author oldAuthor = authorRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Müəllif tapılmadı. ID: " + id));

        oldAuthor.setName(requestDTO.getName());
        oldAuthor.setBiography(requestDTO.getBiography());

        Author newAuthor = authorRepo.save(oldAuthor);

        AuthorResponseDTO responseDTO = new AuthorResponseDTO();
        responseDTO.setId(newAuthor.getId());
        responseDTO.setName(newAuthor.getName());
        responseDTO.setBiography(newAuthor.getBiography());

        return responseDTO;
    }

    public void deleteAuthor(Long id) {
        if (!authorRepo.existsById(id)) {
            throw new RuntimeException("Müəllif tapılmadı. ID: " + id);
        }
        authorRepo.deleteById(id);
    }
}
