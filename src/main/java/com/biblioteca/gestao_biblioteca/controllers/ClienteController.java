package com.biblioteca.gestao_biblioteca.controllers;

import com.biblioteca.gestao_biblioteca.dtos.CreateClientDTO;
import com.biblioteca.gestao_biblioteca.dtos.Response.ResponseApi;
import com.biblioteca.gestao_biblioteca.exceptions.ContentAlreadyExistsException;
import com.biblioteca.gestao_biblioteca.service.ClientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/cliente")
public class ClienteController {

    private final ClientService service;

    public ClienteController(ClientService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ResponseApi> criarCliente(@RequestBody CreateClientDTO dto) throws ContentAlreadyExistsException {
        try {
            service.create(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseApi("Cliente cadastrado com sucesso!", null));
        }catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(new ResponseApi(e.getReason(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseApi("Erro ao cadastrar cliente", null));
        }
    }

    @GetMapping
    public ResponseEntity<ResponseApi> listar(){
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseApi("Todos clientes do sistema", service.listar()));
    }

}