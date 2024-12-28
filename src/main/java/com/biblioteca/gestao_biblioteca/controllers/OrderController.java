package com.biblioteca.gestao_biblioteca.controllers;

import com.biblioteca.gestao_biblioteca.models.Order;
import com.biblioteca.gestao_biblioteca.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<Void> createOrder(@RequestBody Order order) {
        orderService.createOrder(order);
        return ResponseEntity.ok().build();
    }
}
