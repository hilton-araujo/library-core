package com.biblioteca.gestao_biblioteca.controllers;

import com.biblioteca.gestao_biblioteca.dtos.EnumsRespostas;
import com.biblioteca.gestao_biblioteca.enums.OrderTypeEnum;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/enums")
@Tag(name = "Enums API", description = "API para listar valores enum utilizados no sistema.")
public class EnumController {

    @GetMapping("/order-type")
    @Operation(summary = "Listar os tipos de pedidos", description = "Retorna uma lista com todos os tipos de pedidos disponíveis no sistema.")
    public List<EnumsRespostas> listarTiposDePedidos() {
        return Arrays.stream(OrderTypeEnum.values())
                .map(orderType -> new EnumsRespostas(orderType.getValue(), orderType.toString()))
                .collect(Collectors.toList());
    }
}
