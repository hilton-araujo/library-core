package com.biblioteca.gestao_biblioteca.controllers;

import com.biblioteca.gestao_biblioteca.dtos.request.WorkflowRequestDTO;
import com.biblioteca.gestao_biblioteca.dtos.response.ResponseApi;
import com.biblioteca.gestao_biblioteca.service.WorkflowService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/workflow")
public class WorkflowController {

    @Autowired
    private WorkflowService service;

    @PostMapping
    @Operation(summary = "Cria um novo workflow no sistema")
    public ResponseEntity<ResponseApi> criarWorkflow(@RequestBody @Valid WorkflowRequestDTO dto) {
        try {
            service.criarWorkflow(dto);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ResponseApi("Workflow cadastrado com sucesso!", null));
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode())
                    .body(new ResponseApi(e.getReason(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseApi("Erro ao cadastrar workflow", null));
        }
    }

    @GetMapping
    @Operation(summary = "Lista todos os workflows cadastrados no sistema")
    public ResponseEntity<ResponseApi> listar() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseApi("Lista de todos os workflows do sistema", service.listar()));
    }
}
