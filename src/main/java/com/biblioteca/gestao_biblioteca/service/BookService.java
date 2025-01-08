package com.biblioteca.gestao_biblioteca.service;

import com.biblioteca.gestao_biblioteca.dtos.request.BookRequestDTO;
import com.biblioteca.gestao_biblioteca.dtos.request.BookUpdateDTO;
import com.biblioteca.gestao_biblioteca.dtos.response.*;
import com.biblioteca.gestao_biblioteca.functions.GeneratorCode;
import com.biblioteca.gestao_biblioteca.functions.ISBNGenerator;
import com.biblioteca.gestao_biblioteca.models.Book;
import com.biblioteca.gestao_biblioteca.models.Category;
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

    public void registrarBook(BookRequestDTO dto) {
        try {
            Book book;

            Category category = generoRepository.findByCode(dto.categoryCode())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoria não encontrado"));

            boolean existByTittle = repository.existsByTitle(dto.title());
            if (existByTittle) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Livro com o titulo " + dto.title() + " já existe");
            }

            book = new Book();
            book.setCode(GeneratorCode.generateCode());
            book.setTitle(dto.title());
            book.setAuthor(dto.author());
            book.setIsbn(ISBNGenerator.generateISBN10());
            book.setPublisher(dto.publisher());
            book.setLanguage(dto.language());
            book.setLocation(dto.location());
            book.setDescription(dto.description());
            book.setAvailableQuantity(dto.availableQuantity());
            book.setPageCount(dto.pageCount());
            book.setPublishYear(dto.publishYear());
            book.setRating(dto.rating());
            book.setCategory(category);
            book.setImage(dto.image());
            book.setActive(true);

            create(book);

        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao registrar livro", e);
        }
    }

    public void atualizarBook(BookUpdateDTO dto) {
        try {
            Book book = repository.findByCode(dto.code())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Livro não encontrado"));

            Category category = generoRepository.findByCode(dto.categoryCode())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Genero não encontrado"));


            if (!book.getTitle().equals(dto.title())) {
                boolean existByTittle = repository.existsByTitle(dto.title());
                if (existByTittle) {
                    throw new ResponseStatusException(HttpStatus.CONFLICT, "Livro com o titulo " + dto.title() + " já existe");
                }
            }

            book.setCode(dto.code());
            book.setTitle(dto.title());
            book.setAuthor(dto.author());
            book.setPublisher(dto.publisher());
            book.setLanguage(dto.language());
            book.setLocation(dto.location());
            book.setDescription(dto.description());
            book.setAvailableQuantity(dto.availableQuantity());
            book.setPageCount(dto.pageCount());
            book.setPublishYear(dto.publishYear());
            book.setRating(dto.rating());
            book.setCategory(category);
            book.setImage(dto.image());
            book.setActive(true);

            this.create(book);

        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao atualizar livro", e);
        }
    }

    public List<BookResponseDTO> listar(String title, String author, String category) {
        List<Book> books;

        if (title != null && author != null && category != null) {
            books = repository.findByTitleContainingAndAuthorContainingAndCategory_CategoryContaining(title, author, category);
        } else if (title != null && author != null) {
            books = repository.findByTitleContainingAndAuthorContaining(title, author);
        } else if (title != null && category != null) {
            books = repository.findByTitleContainingAndCategory_CategoryContaining(title, category);
        } else if (author != null && category != null) {
            books = repository.findByAuthorContainingAndCategory_CategoryContaining(author, category);
        } else if (title != null) {
            books = repository.findByTitleContaining(title);
        } else if (author != null) {
            books = repository.findByAuthorContaining(author);
        } else if (category != null) {
            books = repository.findByCategory_CategoryContaining(category);
        } else {
            books = repository.findAll();
        }

        List<BookResponseDTO> dtos = new ArrayList<>();
        for (Book book : books) {
            BookResponseDTO bookDTO = new BookResponseDTO(
                    book.getCode(),
                    book.getTitle(),
                    book.getAuthor(),
                    book.getIsbn(),
                    book.getPublisher(),
                    book.getLanguage(),
                    book.getLocation(),
                    book.getDescription(),
                    book.getAvailableQuantity(),
                    book.getPageCount(),
                    book.getPublishYear(),
                    book.getRating(),
                    book.getCategory().getCategory(),
                    book.getCategory().getCode(),
                    book.getImage(),
                    book.getActive()
            );
            dtos.add(bookDTO);
        }
        return dtos;
    }


    public List<Book> listarLivrosEmDestaque() {
        return repository.findByIsHighlightedTrue();
    }


    public BookDetailsDTO listarPorCode(String code) {
        Book book = repository.findByCode(code)
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

        List<LoanDTO>  loanDTOS = book.getLoans().stream()
                .map(comment -> new LoanDTO(
                        comment.getId(),
                        comment.getUser().getName(),
                        comment.getBook().getTitle(),
                        comment.getDataEmprestimo(),
                        comment.getDataDevolucao(),
                        comment.getStatus()

                ))
                .toList();

        return new BookDetailsDTO(
                book.getCode(),
                book.getTitle(),
                book.getAuthor(),
                book.getIsbn(),
                book.getPublisher(),
                book.getLanguage(),
                book.getLocation(),
                book.getDescription(),
                book.getAvailableQuantity(),
                book.getPageCount(),
                book.getPublishYear(),
                book.getRating(),
                book.getCategory().getCategory(),
                book.getCategory().getCode(),
                book.getImage(),
                book.getActive(),
                loanDTOS,
                commentDTOs
        );
    }

}
