package com.example.librarymanagement.repository;

import com.example.librarymanagement.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepe extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {

        Optional<Book> findByIsbn(String isbn);

        //janra görə çağırma
        List<Book> findByTitleContainingIgnoreCaseAndGenreOrderByTitleAsc(String title, String genre);

    //kateqoriyaya görə çağırma
        @Query("SELECT b FROM Book b JOIN b.categories c WHERE LOWER(c.name) = LOWER(:categoryName)")
        List<Book> findBooksByCategoryName(@Param("categoryName") String categoryName);


//top 5 kitablar
        @Query(value = """
            SELECT b.* FROM books b
            JOIN borrow_items bi ON b.id = bi.book_id
            GROUP BY b.id
            ORDER BY COUNT(bi.id) DESC
            LIMIT :limit
            """, nativeQuery = true)
        List<Book> findTopBorrowedBooks(@Param("limit") int limit);


    //checkpoint 5
    @Override
    @EntityGraph(attributePaths = {"author", "categories"})
    Page<Book> findAll(Pageable pageable);

    @Override
    @EntityGraph(attributePaths = {"author", "categories"})
    Page<Book> findAll(Specification<Book> spec, Pageable pageable);

    @Override
    @EntityGraph(attributePaths = {"author", "categories"})
    Optional<Book> findById(Long id);
    }