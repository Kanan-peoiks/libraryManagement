package com.example.librarymanagement.specification;

import com.example.librarymanagement.dto.BookSearch;
import com.example.librarymanagement.model.Book;
import com.example.librarymanagement.model.Category;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class BookSpecification {

    public static Specification<Book> filterBooks(BookSearch search) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            //Title üzrə axtarış
            if (StringUtils.hasText(search.getTitle())) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("title")),
                        "%" + search.getTitle().toLowerCase() + "%"
                ));
            }

            // 2. Genre üzrə axtarış
            if (StringUtils.hasText(search.getGenre())) {
                predicates.add(criteriaBuilder.equal(
                        criteriaBuilder.lower(root.get("genre")),
                        search.getGenre().toLowerCase()
                ));
            }

            // 3. Author ID üzrə sorğu
            if (search.getAuthorId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("author").get("id"), search.getAuthorId()));
            }

            // 4. Category ID üzrə sorğu
            if (search.getCategoryId() != null) {
                Join<Book, Category> categoryJoin = root.join("categories");
                predicates.add(criteriaBuilder.equal(categoryJoin.get("id"), search.getCategoryId()));
            }

            //təkrarlanmama üçün
            query.distinct(true);
            //bir kitabın sorğu zamanı bir neçə dəfə gəlməməsi üçün

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}