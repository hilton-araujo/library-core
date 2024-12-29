package com.biblioteca.gestao_biblioteca.dtos.response;

public record OrderResponseDTO(

        String id,

        String code,

        String designation,

        String description,

        String client,

        String orderType
) {
}
