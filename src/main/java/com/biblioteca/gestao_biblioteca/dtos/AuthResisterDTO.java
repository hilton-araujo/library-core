package com.biblioteca.gestao_biblioteca.dtos;

import com.biblioteca.gestao_biblioteca.enums.Papel;

public record AuthResisterDTO(

        String username,

        String password,

        Papel roles
) {
}
