package com.biblioteca.gestao_biblioteca.controllers;

import com.biblioteca.gestao_biblioteca.dtos.request.TaskRequestDTO;
import com.biblioteca.gestao_biblioteca.dtos.response.ResponseApi;
import com.biblioteca.gestao_biblioteca.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(value = "/api/task")
public class TaskController {

    @Autowired
    private TaskService service;

    @PostMapping
    @Operation(summary = "Cadastra uma nova tarefa no sistema")
    public ResponseEntity<ResponseApi> criarTarefa(@RequestBody @Valid TaskRequestDTO dto){
        try {
            service.registrarTarefa(dto);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ResponseApi("Tarefa cadastrada com sucesso!", null));
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode())
                    .body(new ResponseApi(e.getReason(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseApi("Erro ao cadastrar tarefa", null));
        }
    }

    @GetMapping
    @Operation(summary = "Lista todas as tarefas cadastradas no sistema")
    public ResponseEntity<ResponseApi> listar(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseApi("Lista de tarefas do sistema", service.listar()));
    }
}
