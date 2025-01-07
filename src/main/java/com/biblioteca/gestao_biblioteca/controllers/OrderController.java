package com.biblioteca.gestao_biblioteca.controllers;

import com.biblioteca.gestao_biblioteca.dtos.request.OrderRequestDTO;
import com.biblioteca.gestao_biblioteca.dtos.response.ResponseApi;
import com.biblioteca.gestao_biblioteca.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(value = "/api/order")
public class OrderController {

    private final OrderService service;

    public OrderController(OrderService orderService) {
        this.service = orderService;
    }

    @PostMapping
    @Operation(summary = "Cadastra um novo pedido no sistema")
    public ResponseEntity<ResponseApi> createOrder(@RequestBody @Valid OrderRequestDTO dto) {
        try {
            service.createOrder(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseApi("Pedido cadastrado com sucesso!", null));
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(new ResponseApi(e.getReason(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseApi("Erro ao cadastrar pedido", null));
        }
    }

    @GetMapping
    @Operation(summary = "Lista todos os pedidos registrados no sistema")
    public ResponseEntity<ResponseApi> listar() {
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseApi("Todos pedidos do sistema", service.listar()));
    }
}
