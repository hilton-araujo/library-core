package com.biblioteca.gestao_biblioteca.service;

import com.biblioteca.gestao_biblioteca.dtos.AtualizarLivroDTO;
import com.biblioteca.gestao_biblioteca.dtos.CreateBookDTO;
import com.biblioteca.gestao_biblioteca.dtos.Response.BookDTO;
import com.biblioteca.gestao_biblioteca.dtos.Response.BookListDTO;
import com.biblioteca.gestao_biblioteca.dtos.Response.CommentDTO;
import com.biblioteca.gestao_biblioteca.dtos.Response.ReplyDTO;
import com.biblioteca.gestao_biblioteca.models.Book;
import com.biblioteca.gestao_biblioteca.models.Genero;
import com.biblioteca.gestao_biblioteca.repository.BookRepository;
import com.biblioteca.gestao_biblioteca.repository.GeneroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookRepository repository;

    @Autowired
    private GeneroRepository generoRepository;

    public Book create(Book book) {
        return repository.save(book);
    }

    public void registrarBook(CreateBookDTO dto) {
        try {
            Book book;

            Genero genero = generoRepository.findById(dto.generoId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Genero não encontrado"));

            boolean existByTittle = repository.existsByTitulo(dto.titulo());
            if (existByTittle) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Livro com o titulo " + dto.titulo() + " já existe");
            }

            book = new Book();
            book.setTitulo(dto.titulo());
            book.setAutor(dto.autor());
            book.setGenero(genero);
            book.setQuantidadeDisponível(dto.quantidadeDisponível());
            book.setDescription(dto.description());
            book.setPublishYear(dto.publishYear());
            book.setPageCount(dto.pageCount());
            book.setRating(dto.rating());
            book.setHighlighted(dto.isHighlighted());
            book.setImage(dto.image());

            create(book);

        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao registrar livro", e);
        }
    }

    public void atualizarBook(AtualizarLivroDTO dto) {
        try {
            Book book = repository.findById(dto.id())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Livro não encontrado"));

            Genero genero = generoRepository.findById(dto.generoId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Genero não encontrado"));


            if (!book.getTitulo().equals(dto.titulo())) {
                boolean existByTittle = repository.existsByTitulo(dto.titulo());
                if (existByTittle) {
                    throw new ResponseStatusException(HttpStatus.CONFLICT, "Livro com o titulo " + dto.titulo() + " já existe");
                }
            }

            book = new Book();
            book.setId(dto.id());
            book.setTitulo(dto.titulo());
            book.setAutor(dto.autor());
            book.setGenero(genero);
            book.setQuantidadeDisponível(dto.quantidadeDisponível());
            book.setDescription(dto.description());
            book.setPublishYear(dto.publishYear());
            book.setPageCount(dto.pageCount());
            book.setRating(dto.rating());
            book.setImage(dto.image());

            create(book);

        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao atualizar livro", e);
        }
    }

    public List<BookListDTO> listar(String title, String author, String genero) {
        List<Book> books;

        if (title != null && author != null && genero != null) {
            books = repository.findByTituloContainingAndAutorContainingAndGenero_GeneroContaining(title, author, genero);
        } else if (title != null && author != null) {
            books = repository.findByTituloContainingAndAutorContaining(title, author);
        } else if (title != null && genero != null) {
            books = repository.findByTituloContainingAndGenero_GeneroContaining(title, genero);
        } else if (author != null && genero != null) {
            books = repository.findByAutorContainingAndGenero_GeneroContaining(author, genero);
        } else if (title != null) {
            books = repository.findByTituloContaining(title);
        } else if (author != null) {
            books = repository.findByAutorContaining(author);
        } else if (genero != null) {
            books = repository.findByGenero_GeneroContaining(genero);
        } else {
            books = repository.findAll();
        }

        List<BookListDTO> dtos = new ArrayList<>();
        for (Book book : books) {
            BookListDTO bookDTO = new BookListDTO(
                    book.getId(),
                    book.getTitulo(),
                    book.getAutor(),
                    book.getGenero().getGenero(),
                    book.getQuantidadeDisponível(),
                    book.getDescription(),
                    book.getImage(),
                    book.getPublishYear(),
                    book.getPageCount(),
                    book.getRating(),
                    book.getIsFavority()
            );
            dtos.add(bookDTO);
        }
        return dtos;
    }


    public List<Book> listarLivrosEmDestaque() {
        return repository.findByIsHighlightedTrue();
    }


    public BookDTO listarPorId(String id) {
        Book book = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Livro não encontrado"));

        List<CommentDTO> commentDTOs = book.getComments().stream()
                .map(comment -> new CommentDTO(
                        comment.getId(),
                        comment.getCliente().getName(),
                        comment.getComment(),
                        comment.getCommentDate(),
                        comment.getReplies().stream()
                                .map(reply -> new ReplyDTO(
                                        reply.getId(),
                                        reply.getCliente().getName(),
                                        reply.getReply(),
                                        reply.getReplyDate()
                                ))
                                .toList()
                ))
                .toList();

        return new BookDTO(
                book.getId(),
                book.getTitulo(),
                book.getAutor(),
                book.getGenero().getGenero(),
                book.getQuantidadeDisponível(),
                book.getDescription(),
                book.getImage(),
                book.getPublishYear(),
                book.getPageCount(),
                book.getRating(),
                commentDTOs
        );
    }

}
