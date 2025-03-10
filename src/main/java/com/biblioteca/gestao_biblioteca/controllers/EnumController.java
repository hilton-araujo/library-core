package com.biblioteca.gestao_biblioteca.controllers;

import com.biblioteca.gestao_biblioteca.dtos.EnumsRespostas;
import com.biblioteca.gestao_biblioteca.enums.DocumentType;
import com.biblioteca.gestao_biblioteca.enums.Gender;
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

    @GetMapping("/order-types")
    @Operation(summary = "Listar os tipos de pedidos", description = "Retorna um enum de tipos de pedido.")
    public List<EnumsRespostas> listarTiposDePedidos() {
        return Arrays.stream(OrderTypeEnum.values())
                .map(orderType -> new EnumsRespostas(orderType.getValue(), orderType.toString()))
                .collect(Collectors.toList());
    }

    @GetMapping("/document-types")
    @Operation(summary = "Listar os tipos de documentos", description = "Retorna um enum de tipos de documentos.")
    public List<EnumsRespostas> listarTiposDeDocumento() {
        return Arrays.stream(DocumentType.values())
                .map(documentType -> new EnumsRespostas(documentType.getValue(), documentType.toString()))
                .collect(Collectors.toList());
    }

    @GetMapping("/genders")
    @Operation(summary = "Listar os generos", description = "Retorna um enum de generos.")
    public List<EnumsRespostas> listGenders() {
        return Arrays.stream(Gender.values())
                .map(gender -> new EnumsRespostas(gender.getValue(), gender.toString()))
                .collect(Collectors.toList());
    }
}
