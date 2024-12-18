package com.biblioteca.gestao_biblioteca.controllers;

import com.biblioteca.gestao_biblioteca.dtos.CreateGeneroDTO;
import com.biblioteca.gestao_biblioteca.dtos.Response.GeneroDTO;
import com.biblioteca.gestao_biblioteca.dtos.Response.ResponseApi;
import com.biblioteca.gestao_biblioteca.service.GeneroService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(value = "/api/genero")
public class GeneroController {

    @Autowired
    private GeneroService service;

    @PostMapping
    public ResponseEntity<ResponseApi> criarGenero(@RequestBody @Valid CreateGeneroDTO dto){
        try {
            service.registrarGenero(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseApi("Genero cadastrado com sucesso!", null));
        }catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(new ResponseApi(e.getReason(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseApi("Erro ao cadastrar genero", null));
        }
    }

    @GetMapping
    public ResponseEntity<ResponseApi> listar(){
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseApi("Todos Generos do sistema", service.listar()));
    }

    @PutMapping
    public ResponseEntity<ResponseApi> atualizarGenero(@RequestBody @Valid GeneroDTO dto){
        try {
            service.atualizarGenero(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseApi("Genero atualizado com sucesso!", null));
        }catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(new ResponseApi(e.getReason(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseApi("Erro ao atualizado genero", null));
        }
    }
}
