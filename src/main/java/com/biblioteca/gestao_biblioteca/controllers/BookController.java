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
    public ResponseEntity<ResponseApi> registrarLivro(@RequestBody @Valid BookRequestDTO dto) {
        try {
            service.registrarBook(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseApi("Livro cadastrado com sucesso!", null));
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(new ResponseApi(e.getReason(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseApi("Erro ao cadastrar livro", null));
        }
    }

    @GetMapping
    public ResponseEntity<ResponseApi> listar(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String author,
            @RequestParam(required = false) String category
    ) {
        try {
            List<BookResponseDTO> books = service.listar(title, author, category);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseApi("Lista de livros", books));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseApi("Erro ao listar livros", null));
        }
    }


    @GetMapping(value = "/{code}")
    public ResponseEntity<ResponseApi> listarPorCode(@PathVariable String code) throws ChangeSetPersister.NotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseApi("Livro com codigo " + code, service.listarPorCode(code)));
    }

    @PutMapping
    public ResponseEntity<ResponseApi> atualizarLivro(@RequestBody @Valid BookUpdateDTO dto) {
        try {
            service.atualizarBook(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseApi("Livro atualizado com sucesso!", null));
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(new ResponseApi(e.getReason(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseApi("Erro ao atualizar livro", null));
        }
    }

    @PostMapping(value = "/comment")
    public ResponseEntity<ResponseApi> comentar(@RequestBody @Valid CreateCommentDTO dto) {
        try {
            commentService.registarComentario(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseApi("Comentario efectuado com sucesso!", null));
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(new ResponseApi(e.getReason(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseApi("Erro ao efectuar comentario", null));
        }
    }

    @PostMapping(value = "/comment/reply")
    public ResponseEntity<ResponseApi> responder(@RequestBody @Valid CreateReplyDTO dto) {
        try {
            commentService.registarResposta(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseApi("Resposta no comentario efectuado com sucesso!", null));
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(new ResponseApi(e.getReason(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseApi("Erro ao responder comentario", null));
        }
    }

    @GetMapping("/highlighted")
    public ResponseEntity<ResponseApi> listarLivrosEmDestaque() {
        try {
            List<Book> livrosEmDestaque = service.listarLivrosEmDestaque();
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseApi("Livros em destaque", livrosEmDestaque));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseApi("Erro ao listar livros em destaque", null));
        }
    }

}
