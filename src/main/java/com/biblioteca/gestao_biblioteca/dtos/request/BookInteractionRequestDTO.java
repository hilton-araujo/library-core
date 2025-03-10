package com.biblioteca.gestao_biblioteca.dtos.request;

public record BookInteractionRequestDTO(

        String bookCode,

        String clientCode
) {
}
