package com.biblioteca.gestao_biblioteca.controllers;

import com.biblioteca.gestao_biblioteca.dtos.request.CreateWishDTO;
import com.biblioteca.gestao_biblioteca.dtos.response.ResponseApi;
import com.biblioteca.gestao_biblioteca.service.WishService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(value = "/api/wish")
public class WishController {

    @Autowired
    private WishService service;

    @PostMapping
    @Operation(summary = "Registra um novo desejo de livro no sistema")
    public ResponseEntity<ResponseApi> registrarDesejo(@RequestBody CreateWishDTO dto) {
        try {
            service.criarDesejo(dto);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ResponseApi("Livro desejado adicionado com sucesso", null));
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode())
                    .body(new ResponseApi(e.getReason(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseApi("Erro ao registrar desejo de livro", null));
        }
    }

    @GetMapping
    @Operation(summary = "Lista todos os livros desejados cadastrados no sistema")
    public ResponseEntity<ResponseApi> listar() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseApi("Lista de livros desejados no sistema", service.listar()));
    }
}
