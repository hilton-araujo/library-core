package com.biblioteca.gestao_biblioteca.dtos.request;

import com.biblioteca.gestao_biblioteca.enums.Papel;
import jakarta.validation.constraints.NotEmpty;


public record AuthResisterDTO(

        @NotEmpty(message = "O nome de usuário é obrigatório.")
        String username,

        @NotEmpty(message = "A senha é obrigatória.")
        String password,

        @NotEmpty(message = "O papel (role) é obrigatório.")
        Papel roles

) {
}
