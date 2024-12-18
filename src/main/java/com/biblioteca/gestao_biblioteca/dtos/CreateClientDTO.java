package com.biblioteca.gestao_biblioteca.dtos;

public record CreateClientDTO(

        String name,

        String email,

        String senha
) {
}
