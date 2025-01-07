package com.biblioteca.gestao_biblioteca.controllers;

import com.biblioteca.gestao_biblioteca.dtos.request.EmployeeRequestDTO;
import com.biblioteca.gestao_biblioteca.dtos.response.ResponseApi;
import com.biblioteca.gestao_biblioteca.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(value = "/api/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService service;

    @PostMapping
    @Operation(summary = "Registrar um novo funcionário", description = "Cadastra um novo funcionário com as informações fornecidas.")
    public ResponseEntity<ResponseApi> criarFuncionario(@RequestBody @Valid EmployeeRequestDTO dto) {
        try {
            service.registrarFuncionario(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseApi("Funcionário registrado com sucesso!", null));
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(new ResponseApi(e.getReason(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseApi("Erro ao registrar o funcionário. Por favor, tente novamente mais tarde.", null));
        }
    }

    @GetMapping
    @Operation(summary = "Listar todos os funcionários", description = "Retorna a lista de todos os funcionários cadastrados no sistema.")
    public ResponseEntity<ResponseApi> listar() {
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseApi("Lista de funcionários recuperada com sucesso.", service.listar()));
    }
}
