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
@Tag(name = "Enums API")
public class EnumController {

    @GetMapping("/order-type")
    @Operation(summary = "Retorna todos os tipos de pedidos")
    public List<EnumsRespostas> estadosFrequencia(){
        return Arrays.stream(OrderTypeEnum.values())
                .map(OrderTypes->new EnumsRespostas(OrderTypes.getValue(), OrderTypes.toString()))
                .collect(Collectors.toList());
    }
}