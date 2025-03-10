package com.biblioteca.gestao_biblioteca.controllers;

import com.biblioteca.gestao_biblioteca.dtos.request.BookInteractionRequestDTO;
import com.biblioteca.gestao_biblioteca.dtos.response.ResponseApi;
import com.biblioteca.gestao_biblioteca.service.BookInteractionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(value = "/api/book-interaction")
@Tag(name = "Interações nos Livros")
public class BookInteractionController {
//
//   private final BookInteractionService service;
//
//    public BookInteractionController(BookInteractionService service) {
//        this.service = service;
//    }
//
//    @PostMapping(value = "/desired")
//    @Operation(summary = "Registra um novo desejo de livro no sistema")
//    public ResponseEntity<ResponseApi> registrarDesejo(@RequestBody BookInteractionRequestDTO dto) {
//        try {
//            service.createWish(dto);
//            return ResponseEntity.status(HttpStatus.CREATED)
//                    .body(new ResponseApi("Livro desejado adicionado com sucesso", null));
//        } catch (ResponseStatusException e) {
//            return ResponseEntity.status(e.getStatusCode())
//                    .body(new ResponseApi(e.getReason(), null));
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body(new ResponseApi("Erro ao registrar desejo de livro", null));
//        }
//    }
//
//    @PostMapping(value = "/favorites")
//    @Operation(summary = "Adiciona um livros aos faroviros")
//    public ResponseEntity<ResponseApi> registrarFavorito(@RequestBody BookInteractionRequestDTO dto) {
//        try {
//            service.addFavorite(dto);
//            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseApi("Livro adicionado aos favoritos com sucesso", null));
//        } catch (ResponseStatusException e) {
//            return ResponseEntity.status(e.getStatusCode()).body(new ResponseApi(e.getReason(), null));
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseApi("Erro interno do servidor por favor volte a tentar mais tarde", null));
//        }
//    }
//
//    @GetMapping(value = "/favorites")
//    @Operation(summary = "Listar livros favoritos")
//    public ResponseEntity<ResponseApi> listarFavoritos(){
//        return ResponseEntity.status(HttpStatus.OK).body(new ResponseApi("Livros favoritos", service.listarFavorito()));
//    }
//
//    @GetMapping(value = "/desired")
//    @Operation(summary = "Lista todos os livros desejados cadastrados no sistema")
//    public ResponseEntity<ResponseApi> listar() {
//        return ResponseEntity.status(HttpStatus.OK)
//                .body(new ResponseApi("Lista de livros desejados no sistema", service.listar()));
//    }
}
