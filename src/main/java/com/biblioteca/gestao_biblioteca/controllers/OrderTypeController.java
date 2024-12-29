package com.biblioteca.gestao_biblioteca.controllers;

import com.biblioteca.gestao_biblioteca.dtos.request.OrderTypeRequest;
import com.biblioteca.gestao_biblioteca.dtos.response.ResponseApi;
import com.biblioteca.gestao_biblioteca.service.OrderTypeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(value = "/api/order-type")
public class OrderTypeController {

    @Autowired
    private OrderTypeService service;

    @PostMapping
    public ResponseEntity<ResponseApi> criarOrderType(@RequestBody @Valid OrderTypeRequest dto){
        try {
            service.registrarTipoPedido(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseApi("Tipo de pedido cadastrado com sucesso!", null));
        }catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(new ResponseApi(e.getReason(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseApi("Erro ao cadastrar tipo de pedido ", null));
        }
    }

    @GetMapping
    public ResponseEntity<ResponseApi> listar(){
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseApi("Todos tipod de pedido do sistema", service.listar()));
    }
}
