package com.biblioteca.gestao_biblioteca.controllers;

import com.biblioteca.gestao_biblioteca.dtos.request.BookRequestDTO;
import com.biblioteca.gestao_biblioteca.dtos.request.BookUpdateDTO;
import com.biblioteca.gestao_biblioteca.dtos.request.CreateCommentDTO;
import com.biblioteca.gestao_biblioteca.dtos.request.CreateReplyDTO;
import com.biblioteca.gestao_biblioteca.dtos.response.BookResponseDTO;
import com.biblioteca.gestao_biblioteca.dtos.response.ResponseApi;
import com.biblioteca.gestao_biblioteca.models.Book;
import com.biblioteca.gestao_biblioteca.service.BookService;
import com.biblioteca.gestao_biblioteca.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping(value = "/api/book")
public class BookController {

    @Autowired
    private BookService service;

    @Autowired
    private CommentService commentService;

    @PostMapping
    @Operation(summary = "Cadastra um novo livro no sistema")
    public ResponseEntity<ResponseApi> registrarLivro(@RequestBody @Valid BookRequestDTO dto) {
        try {
            service.registrarBook(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseApi("Livro cadastrado com sucesso!", null));
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(new ResponseApi(e.getReason(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseApi("Ocorreu um erro ao cadastrar o livro. Tente novamente.", null));
        }
    }

    @GetMapping
    @Operation(summary = "Lista todos os livros cadastrados")
    public ResponseEntity<ResponseApi> listar(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String author,
            @RequestParam(required = false) String category
    ) {
        try {
            List<BookResponseDTO> books = service.listar(title, author, category);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseApi("Lista de livros encontrados.", books));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseApi("Ocorreu um erro ao buscar os livros. Tente novamente.", null));
        }
    }

    @GetMapping(value = "/{code}")
    @Operation(summary = "Busca detalhes de um livro pelo código")
    public ResponseEntity<ResponseApi> listarPorCode(@PathVariable String code) throws ChangeSetPersister.NotFoundException {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseApi("Detalhes do livro encontrado.", service.listarPorCode(code)));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseApi("Erro ao buscar detalhes do livro.", null));
        }
    }

    @PutMapping
    @Operation(summary = "Atualiza os dados de um livro")
    public ResponseEntity<ResponseApi> atualizarLivro(@RequestBody @Valid BookUpdateDTO dto) {
        try {
            service.atualizarBook(dto);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseApi("Dados do livro atualizados com sucesso!", null));
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(new ResponseApi("Erro: " + e.getReason(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseApi("Ocorreu um erro ao atualizar o livro. Tente novamente.", null));
        }
    }

    @PostMapping(value = "/comment")
    @Operation(summary = "Adiciona um comentário a um livro")
    public ResponseEntity<ResponseApi> comentar(@RequestBody @Valid CreateCommentDTO dto) {
        try {
            commentService.registarComentario(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseApi("Comentário adicionado com sucesso!", null));
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(new ResponseApi("Erro: " + e.getReason(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseApi("Erro ao adicionar comentário. Tente novamente.", null));
        }
    }

    @PostMapping(value = "/comment/reply")
    @Operation(summary = "Adiciona uma resposta a um comentário")
    public ResponseEntity<ResponseApi> responder(@RequestBody @Valid CreateReplyDTO dto) {
        try {
            commentService.registarResposta(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseApi("Resposta ao comentário adicionada com sucesso!", null));
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(new ResponseApi("Erro: " + e.getReason(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseApi("Erro ao responder ao comentário. Tente novamente.", null));
        }
    }

    @GetMapping("/highlighted")
    @Operation(summary = "Lista os livros em destaque")
    public ResponseEntity<ResponseApi> listarLivrosEmDestaque() {
        try {
            List<Book> livrosEmDestaque = service.listarLivrosEmDestaque();
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseApi("Livros em destaque listados com sucesso.", livrosEmDestaque));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseApi("Erro ao listar os livros em destaque. Tente novamente.", null));
        }
    }
}