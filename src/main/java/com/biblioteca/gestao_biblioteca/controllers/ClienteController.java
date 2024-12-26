package com.biblioteca.gestao_biblioteca.controllers;

import com.biblioteca.gestao_biblioteca.dtos.request.AtualizarClienteDTO;
import com.biblioteca.gestao_biblioteca.dtos.request.ClientCreateRequest;
import com.biblioteca.gestao_biblioteca.dtos.response.ResponseApi;
import com.biblioteca.gestao_biblioteca.exceptions.ContentAlreadyExistsException;
import com.biblioteca.gestao_biblioteca.service.ClientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/client")
public class ClienteController {

    private final ClientService service;

    public ClienteController(ClientService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ResponseApi> criarCliente(@RequestBody ClientCreateRequest dto) throws ContentAlreadyExistsException {
        try {
            service.registrar(dto);
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

    @GetMapping(value = "/{code}")
    public ResponseEntity<ResponseApi> listarPorId(@PathVariable String code){
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseApi("Todos clientes do sistema", service.listarPorCode(code)));
    }

    @PutMapping
    public ResponseEntity<ResponseApi> atualizarCliente(@RequestBody AtualizarClienteDTO dto) throws ContentAlreadyExistsException {
        try {
            service.atualizaar(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseApi("Cliente atualizado com sucesso!", null));
        }catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(new ResponseApi(e.getReason(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseApi("Erro ao atualizar cliente", null));
        }
    }

}