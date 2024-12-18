package com.biblioteca.gestao_biblioteca.repository;

import com.biblioteca.gestao_biblioteca.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, String> {
    boolean existsByTitulo(String titulo);

    List<Book> findByIsHighlightedTrue();

    List<Book> findByTituloContaining(String titulo);
    List<Book> findByAutorContaining(String autor);
    List<Book> findByGenero_GeneroContaining(String genero);
    List<Book> findByTituloContainingAndAutorContaining(String titulo, String autor);
    List<Book> findByTituloContainingAndGenero_GeneroContaining(String titulo, String genero);
    List<Book> findByAutorContainingAndGenero_GeneroContaining(String autor, String genero);
    List<Book> findByTituloContainingAndAutorContainingAndGenero_GeneroContaining(String titulo, String autor, String genero);
}
