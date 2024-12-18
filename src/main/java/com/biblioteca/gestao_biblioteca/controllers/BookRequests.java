package com.biblioteca.gestao_biblioteca.controllers;

import com.biblioteca.gestao_biblioteca.dtos.Response.ResponseApi;
import com.biblioteca.gestao_biblioteca.dtos.ValueDTO;
import com.biblioteca.gestao_biblioteca.service.FavorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(value = "/api/actions")
public class BookRequests {

    @Autowired
    private FavorityService service;

    @PostMapping(value = "/favority")
    public ResponseEntity<ResponseApi> registrarFavorito(@RequestBody ValueDTO dto) {
        try {
            service.registrarFavorito(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseApi("Livro adicionado aos favoritos com sucesso", null));
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(new ResponseApi(e.getReason(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseApi("Erro interno do servidor por favor volte a tentar mais tarde", null));
        }
    }
}
