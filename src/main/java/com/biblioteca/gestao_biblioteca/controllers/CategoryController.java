package com.biblioteca.gestao_biblioteca.controllers;

import com.biblioteca.gestao_biblioteca.dtos.request.CreateCategoryDTO;
import com.biblioteca.gestao_biblioteca.dtos.response.GeneroDTO;
import com.biblioteca.gestao_biblioteca.dtos.response.ResponseApi;
import com.biblioteca.gestao_biblioteca.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(value = "/api/category")
public class CategoryController {

    @Autowired
    private CategoryService service;

    @PostMapping
    @Operation(summary = "Cadastra uma nova categoria no sistema")
    public ResponseEntity<ResponseApi> criarCategoria(@RequestBody @Valid CreateCategoryDTO dto) {
        try {
            service.registrarGenero(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseApi("A categoria foi cadastrada com sucesso!", null));
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(new ResponseApi("Falha ao cadastrar categoria: " + e.getReason(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseApi("Ocorreu um erro inesperado ao cadastrar a categoria.", null));
        }
    }

    @GetMapping
    @Operation(summary = "Lista todas as categorias cadastradas no sistema")
    public ResponseEntity<ResponseApi> listar() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseApi("Categorias cadastradas listadas com sucesso.", service.listar()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseApi("Erro ao listar categorias cadastradas.", null));
        }
    }

    @GetMapping(value = "/{code}")
    @Operation(summary = "Retorna os detalhes de uma categoria pelo código")
    public ResponseEntity<ResponseApi> listarPorCode(@PathVariable String code) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseApi("Detalhes da categoria com o código: " + code, service.listarPorCode(code)));
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(new ResponseApi("Categoria com o código fornecido não encontrada: " + e.getReason(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseApi("Erro ao buscar a categoria pelo código.", null));
        }
    }

    @PutMapping
    @Operation(summary = "Atualiza os detalhes de uma categoria existente")
    public ResponseEntity<ResponseApi> atualizarCategoria(@RequestBody @Valid GeneroDTO dto) {
        try {
            service.atualizarGenero(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseApi("A categoria foi atualizada com sucesso!", null));
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(new ResponseApi("Falha ao atualizar categoria: " + e.getReason(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseApi("Ocorreu um erro inesperado ao atualizar a categoria.", null));
        }
    }
}
