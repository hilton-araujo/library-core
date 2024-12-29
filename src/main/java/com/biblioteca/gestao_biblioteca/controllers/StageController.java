package com.biblioteca.gestao_biblioteca.controllers;

import com.biblioteca.gestao_biblioteca.dtos.request.OrderTypeRequest;
import com.biblioteca.gestao_biblioteca.dtos.request.StageRequestDTO;
import com.biblioteca.gestao_biblioteca.dtos.response.ResponseApi;
import com.biblioteca.gestao_biblioteca.service.OrderTypeService;
import com.biblioteca.gestao_biblioteca.service.StageService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(value = "/api/stage")
public class StageController {

    @Autowired
    private StageService service;

    @PostMapping
    public ResponseEntity<ResponseApi> criarEtapa(@RequestBody @Valid StageRequestDTO dto){
        try {
            service.registrarEtapa(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseApi("Etapa cadastrado com sucesso!", null));
        }catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(new ResponseApi(e.getReason(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseApi("Erro ao cadastrar etapa", null));
        }
    }

    @GetMapping
    public ResponseEntity<ResponseApi> listar(){
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseApi("Todas etapas do sistema", service.listar()));
    }
}
