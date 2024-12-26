package com.biblioteca.gestao_biblioteca.controllers;

import com.biblioteca.gestao_biblioteca.dtos.request.CreateLoanDTO;
import com.biblioteca.gestao_biblioteca.dtos.response.ResponseApi;
import com.biblioteca.gestao_biblioteca.service.LoanService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(value = "/api/loan")
public class LoanController {

    @Autowired
    private LoanService service;

    @PostMapping
    public ResponseEntity<ResponseApi> efectuarEmprestimo(@RequestBody @Valid CreateLoanDTO dto){
        try {
            service.registrarEmprestimo(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseApi("Emprestimo efectuado com sucesso!", null));
        }catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(new ResponseApi(e.getReason(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseApi("Erro ao efectuado emprestimo", null));
        }
    }

    @GetMapping
    public ResponseEntity<ResponseApi> listar(){
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseApi("Todos emprestimos de Livros do sistema", service.listar()));
    }
}
