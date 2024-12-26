package com.biblioteca.gestao_biblioteca.repository;

import com.biblioteca.gestao_biblioteca.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, String> {
    boolean existsByTitle(String title);

    List<Book> findByIsHighlightedTrue();

    List<Book> findByTitleContainingAndAuthorContainingAndCategory_CategoryContaining(String title, String author, String category);

    List<Book> findByTitleContainingAndAuthorContaining(String title, String author);

    List<Book> findByTitleContainingAndCategory_CategoryContaining(String title, String category);

    List<Book> findByTitleContaining(String title);

    List<Book> findByAuthorContainingAndCategory_CategoryContaining(String author, String category);

    List<Book> findByAuthorContaining(String author);

    List<Book> findByCategory_CategoryContaining(String category);

    Optional<Book> findByCode(String code);
}
