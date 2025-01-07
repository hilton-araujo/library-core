package com.biblioteca.gestao_biblioteca.controllers;

import com.biblioteca.gestao_biblioteca.dtos.request.CreateLoanDTO;
import com.biblioteca.gestao_biblioteca.dtos.response.ResponseApi;
import com.biblioteca.gestao_biblioteca.service.LoanService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/loan")
public class LoanController {

    @Autowired
    private LoanService service;

    @PostMapping
    @Operation(summary = "Realiza um empréstimo de livro")
    public ResponseEntity<ResponseApi> realizarEmprestimo(@RequestBody @Valid CreateLoanDTO dto) {
        try {
            service.registrarEmprestimo(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseApi("Empréstimo realizado com sucesso!", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseApi("Erro ao realizar empréstimo", null));
        }
    }

    @GetMapping
    @Operation(summary = "Lista todos os empréstimos de livros registrados no sistema")
    public ResponseEntity<ResponseApi> listarEmprestimos() {
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseApi("Lista de todos os empréstimos de livros do sistema", service.listar()));
    }
}
